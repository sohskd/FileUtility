package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.FileCompressorProcessor;
import com.desmond.fileutility.service.FileHelperService;
import com.desmond.fileutility.service.ZipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;


@Service
public class FileCompressorProcessorImpl implements FileCompressorProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(FileCompressorProcessorImpl.class);

    private FileHelperService fileHelperService;
    private FileInProcess fileInProcess;
    private ZipService zipService;

    @Autowired
    public FileCompressorProcessorImpl(FileHelperService fileHelperService, FileInProcess fileInProcess, ZipService zipService) {
        this.fileHelperService = fileHelperService;
        this.fileInProcess = fileInProcess;
        this.zipService = zipService;
    }

    @Override
    public boolean startProcessing(String[] args) {
        LOGGER.info("> FileCompressorProcessorImpl startProcessing");

        // Construct File class
        this.fileInProcess.setFileInput(args[0]);
        this.fileInProcess.setFileOutput(args[1]);
        this.fileInProcess.setMaximumCompressionSizePerFileInMB(Integer.parseInt(args[2]));

        List<File> listOfFiles = this.fileHelperService.getAvailableFoldersToProcess(this.fileInProcess);

        this.fileHelperService.processOnFileOrFolder(listOfFiles);

        return true;
    }
}
