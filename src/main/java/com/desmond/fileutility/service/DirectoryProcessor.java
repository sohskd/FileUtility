package com.desmond.fileutility.service;

import java.io.File;
import java.util.List;

public interface DirectoryProcessor {

    List<String> getAllFilesInDirectory(File dir);
}
