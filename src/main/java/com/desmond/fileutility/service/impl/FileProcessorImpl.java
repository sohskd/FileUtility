package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.FileHelperService;
import com.desmond.fileutility.service.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class FileProcessorImpl implements FileProcessor {

    private FileHelperService fileHelperService;
    private FileInProcess fileInProcess;

    private static Logger LOGGER = LoggerFactory.getLogger(FileProcessorImpl.class);

    public FileProcessorImpl (FileHelperService fileHelperService, FileInProcess fileInProcess) {
        this.fileHelperService = fileHelperService;
        this.fileInProcess = fileInProcess;
    }

    @Override
    public boolean startProcessing(String[] args) {
        LOGGER.info("> FileProcessorImpl startProcessing");

        // Construct File class
        this.fileInProcess.setFileInput(args[0]);
        this.fileInProcess.setFileOutput(args[1]);
        this.fileInProcess.setMaximumCompressionSizePerFileInMB(Integer.parseInt(args[2]));

        this.fileHelperService.getAvailableFoldersToProcess(this.fileInProcess);
        return true;
    }
}
