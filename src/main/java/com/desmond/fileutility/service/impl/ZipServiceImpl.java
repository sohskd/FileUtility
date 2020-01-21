package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.DirectoryProcessor;
import com.desmond.fileutility.service.ZipService;
import com.desmond.fileutility.utils.impl.FileUtility;
import com.desmond.fileutility.utils.impl.FileValidator;
import com.desmond.fileutility.utils.impl.PathNameUtility;
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
    private FileUtility fileUtility;
    private PathNameUtility pathNameUtility;

    @Autowired
    public ZipServiceImpl(FileInProcess fileInProcess, DirectoryProcessor directoryProcessor, FileValidator fileValidator, FileUtility fileUtility, PathNameUtility pathNameUtility) {
        this.fileInProcess = fileInProcess;
        this.directoryProcessor = directoryProcessor;
        this.fileValidator = fileValidator;
        this.fileUtility = fileUtility;
        this.pathNameUtility = pathNameUtility;
    }

    @Override
    public void zipFile(File file) {
        try {
            byte[] totalBytesOfFile = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            long maxBytesPerFile = this.fileInProcess.getMaximumCompressionSizePerFileInMB() * ValueConstants.MEGABYTE;
            String fullZipFullName = this.pathNameUtility.getFullFileName(file.getName(), FileConstants.ZIP);
            FileOutputStream fos = new FileOutputStream(fullZipFullName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            int index = 0;
            int off = 0;
            while (off < totalBytesOfFile.length) {
                String zipFileName = this.pathNameUtility.getZipEntryName(file, String.valueOf(index));
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

//    @Override
//    public void zipFileInFolder(File file, ZipOutputStream zos) {
//        try {
//            byte[] totalBytesOfFile = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
//            long maxBytesPerFile = this.fileInProcess.getMaximumCompressionSizePerFileInMB() * ValueConstants.MEGABYTE;
//
//            int index = 0;
//            int off = 0;
//            while (off < totalBytesOfFile.length) {
//                String zipFileName = this.pathNameUtility.getZipEntryName(file, String.valueOf(index));
//                zos.putNextEntry(new ZipEntry(zipFileName));
//                int len = (int) maxBytesPerFile;
//                len = this.fileValidator.getLengthOfByte(totalBytesOfFile.length, off, len);
//                zos.write(totalBytesOfFile, off, len);
//                zos.closeEntry();
//                index++;
//                off += maxBytesPerFile;
//            }
//        } catch (FileNotFoundException ex) {
//            System.err.format("The file does not exist");
//        } catch (IOException ex) {
//            System.err.println("I/O error: " + ex);
//        }
//    }

    @Override
    public File zipFileInFolder(File file) {
        try {
            byte[] totalBytesOfFile = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            long maxBytesPerFile = this.fileInProcess.getMaximumCompressionSizePerFileInMB() * ValueConstants.MEGABYTE;

            int index = 0;
            int off = 0;

            String fullZipFullName = this.pathNameUtility.getFullFileName(file.getName(), FileConstants.ZIP);
            FileOutputStream fos = new FileOutputStream(fullZipFullName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            while (off < totalBytesOfFile.length) {
                String zipFileName = this.pathNameUtility.getZipEntryName(file, String.valueOf(index));
                zos.putNextEntry(new ZipEntry(zipFileName));
                int len = (int) maxBytesPerFile;
                len = this.fileValidator.getLengthOfByte(totalBytesOfFile.length, off, len);
                zos.write(totalBytesOfFile, off, len);
                zos.closeEntry();
                index++;
                off += maxBytesPerFile;
            }
            zos.close();
            return new File(fullZipFullName);

        } catch (FileNotFoundException ex) {
            System.err.format("The file does not exist");

        } catch (IOException ex) {
            System.err.println("I/O error: " + ex);
        }

        return null;
    }

    @Override
    public void zipFolder(List<String> listOfFiles, File file, ZipOutputStream zos) {
        try {

            int index = 0;

            for (File f : file.listFiles()) {

                if (this.fileValidator.isHiddenFile(f))
                    continue;

                if (f.isFile()) {
                    File returnedZipFile = zipFileInFolder(f);
                    File fileToBeRemoved = writeIntoZip(returnedZipFile, index, zos);
                    this.fileUtility.deleteDir(fileToBeRemoved);
                    index++;
                } else if (f.isDirectory()) {
                    List<String> listOfFilesInDirectory = this.directoryProcessor.getAllFilesInDirectory(f);
                    String fullZipFullName = this.pathNameUtility.getFullFileName(f.getName(), FileConstants.ZIP);
                    FileOutputStream fos = new FileOutputStream(fullZipFullName);
                    ZipOutputStream zosInner = new ZipOutputStream(fos);
                    zipFolder(listOfFilesInDirectory, f, zosInner);
                }
            }
            zos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File writeIntoZip(File file, int index, ZipOutputStream zos) {
        String zipFileName = this.pathNameUtility.getZipEntryName(file, String.valueOf(index));
        try {
            zos.putNextEntry(new ZipEntry(zipFileName));
            byte[] totalBytesOfFile = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            zos.write(totalBytesOfFile, 0, totalBytesOfFile.length);
            zos.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public File unzipFileOrFolder(File file, int index) {
        String fileZip = file.getAbsolutePath();

        // If file not in TestOutputs, fileZip is in temp
//        try {
//            if (fileUtility.checkIfFileDirectoryExist(file.getAbsolutePath())) {
//                fileZip = this.pathNameUtility.getFileOrFolderNameForDecom(file);
//            }
//        } catch (FileNotFoundException e) {
//            fileZip = file.getAbsolutePath();
//        }

        File createDirectory = this.fileUtility.createOrRetrieve(this.pathNameUtility.getCombineFileName(this.pathNameUtility.getPathOfTempForDecom(), index));
        File destDir = createDirectory;
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
        return createDirectory;
    }

    @Override
    public void decompress(File fileToWorkOn) {
        File[] listOfFiles = fileToWorkOn.listFiles();
        File ofile = new File(this.pathNameUtility.getNameOfDecomFile(listOfFiles[0]));
        FileOutputStream fos;
        FileInputStream fis;

        byte[] fileBytes;
        int bytesRead;

        try {
            fos = new FileOutputStream(ofile, true);
            for (File file : listOfFiles) {
                fis = new FileInputStream(file);
                fileBytes = new byte[(int) file.length()];
                bytesRead = fis.read(fileBytes, 0, (int) file.length());
                assert (bytesRead == fileBytes.length);
                assert (bytesRead == (int) file.length());
                fos.write(fileBytes);
                fos.flush();
                fis.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTempFile(File file) {
        this.fileUtility.deleteDir(file);
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
