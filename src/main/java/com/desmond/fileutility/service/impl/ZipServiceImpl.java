package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.DirectoryProcessor;
import com.desmond.fileutility.service.ZipService;
import com.desmond.fileutility.utils.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class ZipServiceImpl implements ZipService {

    private static Logger LOGGER = LoggerFactory.getLogger(ZipServiceImpl.class);

    private FileInProcess fileInProcess;
    private DirectoryProcessor directoryProcessor;
    private FileValidator fileValidator;

    @Autowired
    public ZipServiceImpl(FileInProcess fileInProcess, DirectoryProcessor directoryProcessor, FileValidator fileValidator) {
        this.fileInProcess = fileInProcess;
        this.directoryProcessor = directoryProcessor;
        this.fileValidator = fileValidator;
    }

    @Override
    public void zipFile(File file) {
        try {

            byte[] totalBytesOfFile = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            long maxBytesPerFile = this.fileInProcess.getMaximumCompressionSizePerFileInMB() * ValueConstants.MEGABYTE;

            String fullZipFullName = getFullFileName(file.getName(), FileConstants.ZIP);
            FileOutputStream fos = new FileOutputStream(fullZipFullName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            int index = 0;
            int off = 0;
            while (off < totalBytesOfFile.length) {
                String zipFileName = getZipEntryName(file, String.valueOf(index));
                zos.putNextEntry(new ZipEntry(zipFileName));
                int len = (int) maxBytesPerFile;
                len = this.fileValidator.getLengthOfByte(totalBytesOfFile.length, off, len);
                zos.write(totalBytesOfFile, off, len);
                zos.closeEntry();
                index++;
                off += maxBytesPerFile;
            }

            zos.close();

        } catch (FileNotFoundException ex) {
            System.err.format("The file does not exist");
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex);
        }
    }

    @Override
    public void zipFileInFolder(File file, ZipOutputStream zos) {
        try {

            byte[] totalBytesOfFile = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            long maxBytesPerFile = this.fileInProcess.getMaximumCompressionSizePerFileInMB() * ValueConstants.MEGABYTE;

            int index = 0;
            int off = 0;
            while (off < totalBytesOfFile.length) {


                String zipFileName = getZipEntryName(file, String.valueOf(index));

                zos.putNextEntry(new ZipEntry(zipFileName));
                int len = (int) maxBytesPerFile;
                len = this.fileValidator.getLengthOfByte(totalBytesOfFile.length, off, len);

                zos.write(totalBytesOfFile, off, len);
                zos.closeEntry();

                index++;
                off += maxBytesPerFile;
            }

//            zos.close();

        } catch (FileNotFoundException ex) {
            System.err.format("The file does not exist");
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex);
        }
    }

//    @Override
//    public void zipFolder(List<String> listOfFiles, File file) {
//
//        String zipDirOutputName = getZipFolderOutputName(file, FileConstants.ZIP);
//        String dirInput = file.getAbsolutePath();
//        File fileInput = new File(dirInput);
//
//        FileOutputStream fos;
//        try {
//            fos = new FileOutputStream(zipDirOutputName);
//            ZipOutputStream zos = new ZipOutputStream(fos);
//            for (String filePath : listOfFiles) {
//
//                zipFile(new File(filePath));
////                System.out.println("Zipping "+filePath);
////                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
////                String zipEntryName = filePath.substring(fileInput.getAbsolutePath().length()+1, filePath.length());
////                ZipEntry ze = new ZipEntry(zipEntryName);
////                zos.putNextEntry(ze);
////                //read the file and write to ZipOutputStream
////                FileInputStream fis = new FileInputStream(filePath);
////                byte[] buffer = new byte[1024];
////                int len;
////                while ((len = fis.read(buffer)) > 0) {
////                    zos.write(buffer, 0, len);
////                }
////                zos.closeEntry();
////                fis.close();
//            }
//            zos.close();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void zipFolder(List<String> listOfFiles, File file) {

        boolean isFolder = true;

        // /Users/pcdessy/Library/Mobile Documents/com~apple~CloudDocs/Desmond/Jobs \
        // /Job Specific Attachments/Agoda/TestFileFolders/TestOutput/test2dir.zip
        String zipDirOutputName = getZipFolderOutputName(file, FileConstants.ZIP);

        // /Users/pcdessy/Library/Mobile Documents/com~apple~CloudDocs/Desmond \
        // /Jobs/Job Specific Attachments/Agoda/TestFileFolders/TestInput/test2dir
        String dirInput = file.getAbsolutePath();
        File fileInput = new File(dirInput);

        String fullZipFullName = getFullFileName(file.getName(), FileConstants.ZIP);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fullZipFullName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            String absPathOfCurFile;
            String parentPathOfCurFile;
            String parentCurFile;

            // Check files and folders within the folder
            for (String filePath : listOfFiles) {
                File curFile = new File(filePath);
                absPathOfCurFile = curFile.getAbsolutePath();
                parentPathOfCurFile = curFile.getParent();
                parentCurFile = new File(curFile.getParent()).getName();
                if (curFile.isFile()) {
                    // Add Entry to Zip
                    zipFileInFolder(curFile, zos);
                } else {
                    LOGGER.info("handle folder");
                    List<String> listOfFilesInDirectory = this.directoryProcessor.getAllFilesInDirectory(curFile);
                    LOGGER.info("here");
                }
            }

            zos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void zipFolder(List<String> listOfFiles, File file) {
//
//            String zipDirOutputName = getZipFolderOutputName(file, FileConstants.ZIP);
//
//            // /Users/pcdessy/Library/Mobile Documents/com~apple~CloudDocs/Desmond \
//            // /Jobs/Job Specific Attachments/Agoda/TestFileFolders/TestInput/test2dir
//            String dirInput = file.getAbsolutePath();
//            File fileInput = new File(dirInput);
//
//            FileOutputStream fos;
//            try {
//                fos = new FileOutputStream(zipDirOutputName);
//                ZipOutputStream zos = new ZipOutputStream(fos);
//                for(String filePath : listOfFiles){
//                    System.out.println("Zipping "+filePath);
//                    //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
//                    String zipEntryName = filePath.substring(fileInput.getAbsolutePath().length()+1, filePath.length());
//                    ZipEntry ze = new ZipEntry(zipEntryName);
//                    zos.putNextEntry(ze);
//                    //read the file and write to ZipOutputStream
//                    FileInputStream fis = new FileInputStream(filePath);
//                    byte[] buffer = new byte[1024];
//                    int len;
//                    while ((len = fis.read(buffer)) > 0) {
//                        zos.write(buffer, 0, len);
//                    }
//                    zos.closeEntry();
//                    fis.close();
//                }
//                zos.close();
//                fos.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//    }

    public String getFullFileName(String fileName, String extension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fileInProcess.getFileOutput());
        stringBuilder.append("/");
        stringBuilder.append(fileName);
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

    private String getZipFolderOutputName(File file, String extension) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fileInProcess.getFileOutput());
        stringBuilder.append("/");
        stringBuilder.append(file.getName());
        stringBuilder.append(extension);
        return stringBuilder.toString();
    }

    @Override
    public void unzipFileOfFolder(File file) {
        String fileZip = "/Users/pcdessy/Library/Mobile Documents/com~apple~CloudDocs/Desmond/Jobs/Job Specific Attachments/Agoda/TestFileFolders/TestOutput/" + file.getName();
        File destDir = new File("/Users/pcdessy/Library/Mobile Documents/com~apple~CloudDocs/Desmond/Jobs/Job Specific Attachments/Agoda/TestFileFolders/TestOutputAfterDecom");
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
