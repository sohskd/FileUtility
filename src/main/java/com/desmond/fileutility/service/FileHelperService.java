package com.desmond.fileutility.service;

import com.desmond.fileutility.model.FileInProcess;

import java.io.File;
import java.util.List;

public interface FileHelperService {

    List<File> getAvailableFoldersToProcess(FileInProcess fileInProcess);

    void processOnFileOrFolder(List<File> listOfFiles);

    void decomOnFileOrFolder(List<File> listOfFiles, int index);

}
