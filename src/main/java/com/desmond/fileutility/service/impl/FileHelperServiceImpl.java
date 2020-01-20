package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.DirectoryProcessor;
import com.desmond.fileutility.service.FileHelperService;
import com.desmond.fileutility.service.ZipService;
import com.desmond.fileutility.utils.impl.FileValidator;
import com.desmond.fileutility.utils.impl.PathNameUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipOutputStream;

@Service
public class FileHelperServiceImpl implements FileHelperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelperServiceImpl.class);

    private ZipService zipService;
    private DirectoryProcessor directoryProcessor;
    private FileValidator fileValidator;
    private PathNameUtility pathNameUtility;

    @Autowired
    public FileHelperServiceImpl(ZipService zipService, DirectoryProcessor directoryProcessor, FileValidator fileValidator, PathNameUtility pathNameUtility) {
        this.zipService = zipService;
        this.directoryProcessor = directoryProcessor;
        this.fileValidator = fileValidator;
        this.pathNameUtility = pathNameUtility;
    }

    @Override
    public List<File> getAvailableFoldersToProcess(FileInProcess fileInProcess) {
        LOGGER.info("> FileHelperServiceImpl getAvailableFoldersToProcess(FileInProcess fileInProcess)");
        File[] allFolderPath = this.getAllFolderPath(fileInProcess);
        List<File> listOfFiles = Arrays.asList(allFolderPath);
        return listOfFiles;
    }

    @Override
    public void processOnFileOrFolder(List<File> listOfFiles) {
        LOGGER.info("> FileHelperServiceImpl processOnFileOrFolder(List<File> listOfFiles)");

        for (File file : listOfFiles) {

            if (this.fileValidator.isHiddenFile(file))
                continue;

            if (file.isFile()) {
                this.zipService.zipFile(file);
            } else if (file.isDirectory()) {
                List<String> listOfFilesInDirectory = this.directoryProcessor.getAllFilesInDirectory(file);
                String fullZipFullName = this.pathNameUtility.getFullFileName(file.getName(), FileConstants.ZIP);
                try {
                    FileOutputStream fos = new FileOutputStream(fullZipFullName);
                    ZipOutputStream zos = new ZipOutputStream(fos);
                    this.zipService.zipFolder(listOfFilesInDirectory, file, zos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void decomOnFileOrFolder(List<File> listOfFiles) {
        for (File file : listOfFiles) {
            if (this.fileValidator.isHiddenFile(file))
                continue;
            if (file.isFile()) {
                File fileTemp = this.zipService.unzipFileOrFolder(file);
                this.zipService.decompress(fileTemp);
                this.zipService.deleteTempFile(fileTemp);
            } else if (file.isDirectory()) {
                List<String> listOfFilesInDirectory = this.directoryProcessor.getAllFilesInDirectory(file);
                String fullZipFullName = this.pathNameUtility.getFullFileName(file.getName(), FileConstants.ZIP);
                try {
                    FileOutputStream fos = new FileOutputStream(fullZipFullName);
                    ZipOutputStream zos = new ZipOutputStream(fos);
                    this.zipService.zipFolder(listOfFilesInDirectory, file, zos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected File[] getAllFolderPath(FileInProcess fileInProcess) {
        String folderToSearch = fileInProcess.getFileInput();
        File root = new File(folderToSearch);
        File[] allFolderPath = root.listFiles();
        return allFolderPath;
    }
}
