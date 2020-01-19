package com.desmond.fileutility.service;

import java.io.File;
import java.util.List;
import java.util.zip.ZipOutputStream;

public interface ZipService {

    void zipFile(File file);

    void zipFolder(List<String> listOfFiles, File file);

    void zipFileInFolder(File file, ZipOutputStream zos);

    String getFullFileName(String fileName, String extension);

    void unzipFileOfFolder(File file);
}
