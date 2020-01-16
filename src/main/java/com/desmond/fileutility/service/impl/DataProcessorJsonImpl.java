//package com.desmond.fileutility.service.impl;
//
//import com.desmond.fileutility.configs.FolderConfigModel;
//import com.desmond.fileutility.exceptions.UnknownOSException;
//import com.desmond.fileutility.producer.Sender;
//import com.desmond.fileutility.service.DataProcessor;
//import com.desmond.fileutility.service.FileService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//@Service
//@Qualifier("JsonFolderRepo")
//public class DataProcessorJsonImpl implements DataProcessor {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(DataProcessorJsonImpl.class);
//    private static Logger ERRORLOGGER = LoggerFactory.getLogger("errorLogger");
//
//    private FileService fileService;
//    private SenderFactory senderFactory;
//    private FolderConfigModel folderConfigModel;
//
//    @Autowired
//    public DataProcessorJsonImpl(FileService fileService, SenderFactory senderFactory, FolderConfigModel folderConfigModel) {
//        this.fileService = fileService;
//        this.senderFactory = senderFactory;
//        this.folderConfigModel = folderConfigModel;
//    }
//
//    @Override
//    public boolean startProcessing() {
//
//        List<File> folderListToProcess = this.fileService.getAvailableFoldersToProcess();
//
//        if (folderListToProcess != null) {
//            for (File archiveFolder : folderListToProcess) {
//
//                if (!archiveFolder.isDirectory()) {
//                    try {
//                        archiveFolder = this.fileService.createFolder(archiveFolder);
//                    } catch (IOException | UnknownOSException e) {
//                        LOGGER.info(e.getMessage());
//                        ERRORLOGGER.info(e.getMessage());
//                        continue;
//                    }
//                }
//
//                for (File domainFolder : archiveFolder.listFiles()) {
//                    String topic = domainFolder.getName();
//                    if (topic.equals(this.folderConfigModel.getProcessedFileName())) {
//                        for (File jsonFile : domainFolder.listFiles()) {
//                            try {
//                                Sender sender = this.senderFactory.getSender(topic);
//                                sender.prepareData(jsonFile);
//                                sender.send();
//                                this.fileService.sendProcessedFolderToArchive(archiveFolder, domainFolder, jsonFile);
//                            } catch (RecordTooLargeException e) {
//                                LOGGER.info(e.getMessage());
//                                ERRORLOGGER.error(e.getMessage());
//                            } catch (Exception e) {
//                                LOGGER.info(e.getMessage());
//                                ERRORLOGGER.error(e.getMessage());
//                            }
//                        }
//                    }
//                }
//
//                // record that folder name has been processed before
//                this.fileService.recordFolderHasBeenProcessed(archiveFolder.getName());
//
//                // Delete the archive folder
//                this.fileService.deleteDirectory(archiveFolder);
//            }
//            return true;
//        } else {
//            return false;
//        }
//    }
//}