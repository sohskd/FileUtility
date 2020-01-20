package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.service.DirectoryProcessor;
import com.desmond.fileutility.utils.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryProcessorServiceImpl implements DirectoryProcessor {

    private ArrayList<String> arrayListOfFiles;
    private FileValidator fileValidator;

    @Autowired
    public DirectoryProcessorServiceImpl(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    @Override
    public List<String> getAllFilesInDirectory(File dir) {
        try {
            List<String> results = new ArrayList<>();
            results = this.populateFilesList(dir, results);
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> populateFilesList(File dir, List<String> results) throws IOException {
        File[] files = dir.listFiles();
        for (File file : files) {

            if (this.fileValidator.isHiddenFile(file))
                continue;

            if (file.isFile()) {
                results.add(file.getAbsolutePath());
            } else {
                populateFilesList(file, results);
            }
        }
        return results;
    }
}
