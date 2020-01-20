package com.desmond.fileutility.utils;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileUtilityInterface {

    File createOrRetrieve(String directory);

    void deleteDir(File file);

    boolean checkIfFileDirectoryExist(String inputDirectory) throws FileNotFoundException;
}
