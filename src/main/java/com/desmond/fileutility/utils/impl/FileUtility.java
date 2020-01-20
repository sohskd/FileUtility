package com.desmond.fileutility.utils.impl;

import com.desmond.fileutility.utils.FileUtilityInterface;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

@Component
public class FileUtility implements FileUtilityInterface {

    public FileUtility() {};

    public File createOrRetrieve(String directory) {
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
        return new File(directory);
    }

    public void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

    public boolean checkIfFileDirectoryExist(String inputDirectory) throws FileNotFoundException {
        if (!new File(inputDirectory).exists()) {
            throw new FileNotFoundException(inputDirectory + " not found");
        }
        return true;
    }
}
