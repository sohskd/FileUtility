package com.desmond.fileutility.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileInProcess {

    private String fileInput;
    private String fileOutput;
    private int maximumCompressionSizePerFileInMB;
    private List<String> listOfFiles;

    public FileInProcess () {}

    public String getFileInput() {
        return fileInput;
    }

    public void setFileInput(String fileInput) {
        this.fileInput = fileInput;
    }

    public String getFileOutput() {
        return fileOutput;
    }

    public void setFileOutput(String fileOutput) {
        this.fileOutput = fileOutput;
    }

    public int getMaximumCompressionSizePerFileInMB() {
        return maximumCompressionSizePerFileInMB;
    }

    public void setMaximumCompressionSizePerFileInMB(int maximumCompressionSizePerFileInMB) {
        this.maximumCompressionSizePerFileInMB = maximumCompressionSizePerFileInMB;
    }

    public List<String> getListOfFiles() {
        return listOfFiles;
    }

    public void setListOfFiles(List<String> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }
}
