package com.desmond.fileutility.service;

import java.io.File;
import java.util.List;
import java.util.zip.ZipOutputStream;

public interface ZipService {

    void zipFile(File file);

    void zipFolder(List<String> listOfFiles, File file, ZipOutputStream zos);

//    void zipFileInFolder(File file, ZipOutputStream zos);
    File zipFileInFolder(File file);

//    File unzipFileOrFolder(File file);
    File unzipFileOrFolder(File file, int index);

    void decompress(File file);

    void deleteTempFile(File file);
}
