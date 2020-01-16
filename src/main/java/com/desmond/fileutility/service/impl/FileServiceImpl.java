package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.configs.EnvironmentConfig;
import com.desmond.fileutility.configs.FolderConfigModel;
import com.desmond.fileutility.exceptions.UnknownOSException;
import com.desmond.fileutility.service.FileService;
import com.desmond.fileutility.service.filehandlers.FileSpecificHandler;
import com.desmond.fileutility.utils.FileHandlerFactory;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
    private static Logger ERRORLOGGER = LoggerFactory.getLogger("errorLogger");

    private FolderConfigModel folderConfigModel;
    private FileHandlerFactory fileHandlerFactory;
    private EnvironmentConfig environmentConfig;

    @Autowired
    public FileServiceImpl(FolderConfigModel folderConfigModel, FileHandlerFactory fileHandlerFactory, EnvironmentConfig environmentConfig) {
        this.folderConfigModel = folderConfigModel;
        this.fileHandlerFactory = fileHandlerFactory;
        this.environmentConfig = environmentConfig;
    }

    @Override
    public List<File> getAvailableFoldersToProcess() {

        File[] allFolderPath = this.getAllFolderPath();

        List<String> processedFolders = getProcessedFolders();

        List<String> listOfFolderIgnore = this.folderConfigModel.getFolderIgnore();
        // Filter away folders to ignore
        // Filter away folders that has been processed before
        // Filter in folders with "archive_*"
        List<File> fileListToProcess = Arrays.stream(allFolderPath)
                .filter(myFile -> !listOfFolderIgnore.contains(myFile.getName())
                        && !processedFolders.contains(myFile.getName().split("\\.")[0])
                        && (myFile.getName().startsWith("archive_") || myFile.getName().startsWith("archive-")))
                .collect(Collectors.toList());

        if (fileListToProcess.size() == 0)
            fileListToProcess = null;

        return fileListToProcess;
    }

    protected File[] getAllFolderPath() {

        String folderToSearch = this.folderConfigModel.getFolderDataDirectory();
        File root = new File(folderToSearch);
        File[] allFolderPath = root.listFiles();

        return allFolderPath;
    }

    protected List<String> getProcessedFolders() {
        checkIfProcessedFolderLogIsCreated();
        Path path = Paths.get(this.environmentConfig.getEnvironmentWorkingDirectory(), this.folderConfigModel.getProcessedFileName());
        List<String> listOfProcessedFolder = null;
        try {
            listOfProcessedFolder = Files.readAllLines(path);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            ERRORLOGGER.info(e.getMessage());
            e.printStackTrace();
        }

        return listOfProcessedFolder;
    }


    @Override
    public boolean sendProcessedFolderToArchive(File archiveFolder, File domainFolder, File jsonFile) {
        File archiveDirectory;
        archiveDirectory = new File(this.folderConfigModel.getFolderDataDirectory() + File.separator + this.folderConfigModel.getArchiveFolderName());

        String directoryToMoveToStr = archiveDirectory + File.separator + archiveFolder.getName() + File.separator + domainFolder.getName();
        File directoryToMoveTo = new File(directoryToMoveToStr);
        // Check if archivefolder exist. if not, create it.
        if (!directoryToMoveTo.exists()) {
            boolean isdirectoryToMoveToFolderCreated = directoryToMoveTo.mkdirs();
            if (!isdirectoryToMoveToFolderCreated)
                return false;
        }
        String pathToRenameTo = directoryToMoveToStr + File.separator + jsonFile.getName();
        jsonFile.renameTo(new File(pathToRenameTo));

        return true;
    }

    private void checkIfProcessedFolderLogIsCreated() {
        String processedFileLogStr = this.environmentConfig.getEnvironmentWorkingDirectory() + File.separator + this.folderConfigModel.getProcessedFileName();
        File processedFileLog = new File(processedFileLogStr);
        processedFileLog.setWritable(true);
        if (!processedFileLog.exists()) {
            try {
                processedFileLog.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void recordFolderHasBeenProcessed(String folderProcessed) {
        checkIfProcessedFolderLogIsCreated();
        Path path = Paths.get(this.environmentConfig.getEnvironmentWorkingDirectory(), this.folderConfigModel.getProcessedFileName());
        try {
            Files.write(path, (folderProcessed + System.lineSeparator()).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteDirectory(File archiveFolder) {
        try {
            delete(archiveFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    delete(fileDelete);
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } else {
            file.delete();
        }
    }

    @Override
    public File createFolder(File filePath) throws IOException, UnknownOSException {

        String ext = FilenameUtils.getExtension(filePath.getName());
        FileSpecificHandler fileSpecificHandler = this.fileHandlerFactory.getFileHandler(ext);
        File file = fileSpecificHandler.createFolder(filePath, ext);

        return file;
    }
}
