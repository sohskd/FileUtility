package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.DirectoryProcessor;
import com.desmond.fileutility.service.FileHelperService;
import com.desmond.fileutility.service.ZipService;
import com.desmond.fileutility.utils.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileHelperServiceImpl implements FileHelperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelperServiceImpl.class);

    private ZipService zipService;
    private DirectoryProcessor directoryProcessor;
    private FileValidator fileValidator;

    @Autowired
    public FileHelperServiceImpl(ZipService zipService, DirectoryProcessor directoryProcessor, FileValidator fileValidator) {
        this.zipService = zipService;
        this.directoryProcessor = directoryProcessor;
        this.fileValidator = fileValidator;
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
                this.zipService.zipFolder(listOfFilesInDirectory, file);
            }
        }
    }

    @Override
    public void decomOnFileOrFolder(List<File> listOfFiles) {

        for (File file : listOfFiles) {

            if (this.fileValidator.isHiddenFile(file))
                continue;

            if (file.isFile()) {

                // Unzip file
                this.zipService.unzipFileOfFolder(file);

                // Combine file
            } else if (file.isDirectory()) {
                List<String> listOfFilesInDirectory = this.directoryProcessor.getAllFilesInDirectory(file);
                this.zipService.zipFolder(listOfFilesInDirectory, file);
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
