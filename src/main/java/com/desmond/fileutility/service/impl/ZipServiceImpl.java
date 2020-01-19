package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipServiceImpl implements ZipService {

    FileInProcess fileInProcess;

    @Autowired
    public ZipServiceImpl (FileInProcess fileInProcess) {
        this.fileInProcess = fileInProcess;
    }

    @Override
    public void zipFile(List<File> listOfFiles) {
        for (int i = 0; i < listOfFiles.size(); i++) {
            try {
                File file = listOfFiles.get(i);

                byte[] totalBytesOfFile = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                long maxBytesPerFile = this.fileInProcess.getMaximumCompressionSizePerFileInMB() * ValueConstants.MEGABYTE;

                int index = 0;
                int off = 0;
                while (off < totalBytesOfFile.length) {

                    String fullZipFullName = getFullFileName(file, String.valueOf(index), FileConstants.ZIP);
                    FileOutputStream fos = new FileOutputStream(fullZipFullName);
                    ZipOutputStream zos = new ZipOutputStream(fos);

                    String zipFileName = getZipEntryName(file, String.valueOf(index));
                    zos.putNextEntry(new ZipEntry(zipFileName));
                    int len = (int) maxBytesPerFile;

                    zos.write(totalBytesOfFile, off, len);
                    zos.closeEntry();
                    zos.close();

                    index++;
                    off += maxBytesPerFile;
                }

            } catch (FileNotFoundException ex) {
                System.err.format("The file does not exist");
            } catch (IOException ex) {
                System.err.println("I/O error: " + ex);
            }
        }
    }

    private String getFullFileName(File file, String index, String extension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fileInProcess.getFileOutput());
        stringBuilder.append("/");
        stringBuilder.append(file.getName());
        stringBuilder.append(index);
        stringBuilder.append(extension);
        return stringBuilder.toString();
    }

    private String getZipEntryName(File file, String index) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] stringArray = file.getName().split("\\.");
        stringBuilder.append(stringArray[0]);
        stringBuilder.append(index);
        stringBuilder.append(".");
        stringBuilder.append(stringArray[1]);
        return stringBuilder.toString();
    }

//    @Override
//    public void zipFile(List<File> listOfFiles) {
//        for (int i = 0; i < listOfFiles.size(); i++) {
//            try {
//                File file = listOfFiles.get(i);
//                System.out.println(file.getParent());
//                String zipFileName = file.getName().concat(".zip");
//
//                FileOutputStream fos = new FileOutputStream(file.getParent() + "/test6.txt.zip");
////                FileOutputStream fos = new FileOutputStream(zipFileName);
//                ZipOutputStream zos = new ZipOutputStream(fos);
//
//                zos.putNextEntry(new ZipEntry(file.getName()));
//
//                byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
//
//                zos.write(bytes, 0, bytes.length / 3);
//                zos.closeEntry();
//                zos.close();
//
//            } catch (FileNotFoundException ex) {
//                System.err.format("The file does not exist");
//            } catch (IOException ex) {
//                System.err.println("I/O error: " + ex);
//            }
//        }
//    }

//    @Override
//    public void zipFile(List<File> listOfFiles) {
//        for (int i = 0; i < listOfFiles.size(); i++) {
//            try {
//                File file = listOfFiles.get(i);
//                System.out.println(file.getParent());
//                String zipFileName = file.getName().concat(".zip");
//
////                FileOutputStream fos = new FileOutputStream(file.getParent() + "/test6.txt.zip");
////                FileOutputStream fos = new FileOutputStream(zipFileName);
////                ZipOutputStream zos = new ZipOutputStream(fos);
//
////                zos.putNextEntry(new ZipEntry(file.getName()));
//
//                byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
//                long maxPerFile = 20 * ValueConstants.MEGABYTE;
//                int off = 0;
//                int byteLength;
//                int numberOfFile = 0;
//                while (off < bytes.length) {
////                    String s = file.getParent() + "/" + numberOfFile + zipFileName;
//                    FileOutputStream fos = new FileOutputStream(file.getParent() + "/test6.txt.zip");
//                    ZipOutputStream zos = new ZipOutputStream(fos);
//                    byteLength = (int) (off + maxPerFile);
//
//                    if (byteLength < bytes.length)
//                        System.out.println("it is lesser");
//
//                    zos.write(bytes, off, bytes.length);
//                    zos.closeEntry();
//                    zos.close();
//
//                    numberOfFile++;
//                    off += byteLength;
//                }
//
////                zos.write(bytes, 0, bytes.length);
////                zos.closeEntry();
////                zos.close();
//
//            } catch (FileNotFoundException ex) {
//                System.err.format("The file does not exist");
//            } catch (IOException ex) {
//                System.err.println("I/O error: " + ex);
//            }
//        }
//    }
}
