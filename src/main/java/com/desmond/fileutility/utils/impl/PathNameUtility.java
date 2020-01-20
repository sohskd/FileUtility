package com.desmond.fileutility.utils.impl;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.model.FileInProcess;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PathNameUtility {

    public FileInProcess fileInProcess;

    @Autowired
    public PathNameUtility (FileInProcess fileInProcess) {
        this.fileInProcess = fileInProcess;
    }

    public String getFullFileName(String fileName, String extension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fileInProcess.getFileOutput());
        stringBuilder.append("/");
        stringBuilder.append(fileName);
        stringBuilder.append(extension);
        return stringBuilder.toString();
    }

    public String getZipEntryName(File file, String index) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] stringArray = file.getName().split("\\.");
        stringBuilder.append(stringArray[0]);
        stringBuilder.append("_");
        stringBuilder.append(index);
        stringBuilder.append(".");
        stringBuilder.append(FilenameUtils.getExtension(String.valueOf(file)));
        return stringBuilder.toString();
    }

    public String getFileOrFolderNameForDecom(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fileInProcess.getFileInput());
        stringBuilder.append("/");
        stringBuilder.append(file.getName());
        return stringBuilder.toString();
    }

    public String getPathOfTempForDecom() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fileInProcess.getFileOutput());
        stringBuilder.append("/");
        stringBuilder.append(FileConstants.TEMP_FOLDER);
        return stringBuilder.toString();
    }

    public String getNameOfDecomFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fileInProcess.getFileOutput());
        stringBuilder.append("/");
        stringBuilder.append(file.getName().split(ValueConstants.UNDERSCORE)[0]);
        stringBuilder.append(".");
        stringBuilder.append(FilenameUtils.getExtension(String.valueOf(file)));
        return stringBuilder.toString();
    }
}
