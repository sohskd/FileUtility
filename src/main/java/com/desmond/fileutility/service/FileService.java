package com.desmond.fileutility.service;

import com.desmond.fileutility.exceptions.UnknownOSException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {

    List<File> getAvailableFoldersToProcess();

    boolean sendProcessedFolderToArchive(File archiveFolder, File domainFolder, File jsonFile);

    boolean deleteDirectory(File archiveFolder);

    File createFolder(File filePath) throws IOException, UnknownOSException;

    void recordFolderHasBeenProcessed(String folderProcessed);
}
