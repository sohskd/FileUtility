package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.FileHelperService;
import com.desmond.fileutility.service.ZipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class FileHelperServiceImpl implements FileHelperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelperServiceImpl.class);

    private ZipService zipService;

    public FileHelperServiceImpl(ZipService zipService) {
        this.zipService = zipService;
    }

    @Override
    public List<File> getAvailableFoldersToProcess(FileInProcess fileInProcess) {


        LOGGER.info("> FileHelperServiceImpl getAvailableFoldersToProcess(FileInProcess fileInProcess)");
        File[] allFolderPath = this.getAllFolderPath(fileInProcess);

        List<File> listOfFiles = Arrays.asList(allFolderPath);

        this.zipService.zipFile(listOfFiles);
//        this.zipDir.

        return listOfFiles;
    }

    protected File[] getAllFolderPath(FileInProcess fileInProcess) {

        String folderToSearch = fileInProcess.getFileInput();
        File root = new File(folderToSearch);
        File[] allFolderPath = root.listFiles();

        return allFolderPath;
    }


}
