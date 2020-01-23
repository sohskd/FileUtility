package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.FileDecompressorProcessor;
import com.desmond.fileutility.service.FileHelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class FileDecompressorProcessorImpl implements FileDecompressorProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(FileDecompressorProcessorImpl.class);

    private FileInProcess fileInProcess;
    private FileHelperService fileHelperService;

    @Autowired
    public FileDecompressorProcessorImpl(FileHelperService fileHelperService, FileInProcess fileInProcess) {
        this.fileHelperService = fileHelperService;
        this.fileInProcess = fileInProcess;
    }

    @Override
    public boolean startProcessing(String[] args) {
        LOGGER.info("Processing Decompression...");
        this.fileInProcess.setFileInput(args[0]);
        this.fileInProcess.setFileOutput(args[1]);
        List<File> listOfFiles = this.fileHelperService.getAvailableFoldersToProcess(this.fileInProcess);
        int index = 0;
        this.fileHelperService.decomOnFileOrFolder(listOfFiles, index);
        LOGGER.info("Processing Decompression completed.");
        return true;
    }
}
