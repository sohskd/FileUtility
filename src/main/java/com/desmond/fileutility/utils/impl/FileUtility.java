package com.desmond.fileutility.utils.impl;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.service.impl.ZipServiceImpl;
import com.desmond.fileutility.utils.FileUtilityInterface;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtility implements FileUtilityInterface {

    private static Logger LOGGER = LoggerFactory.getLogger(FileUtility.class);

    public FileUtility() {};

    @Override
    public File createOrRetrieve(String directory) {
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
        return new File(directory);
    }

    @Override
    public boolean deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
        return true;
    }

    @Override
    public boolean checkIfFileDirectoryExist(String inputDirectory) throws FileNotFoundException {
        if (!new File(inputDirectory).exists()) {
            throw new FileNotFoundException(inputDirectory + " not found");
        }
        return true;
    }

    @Override
    public List<byte[]> getListOfBytes(File file) {
        try {
            FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
            List<byte[]> listOfBytes = new ArrayList<>();
            double index = 0;
            int totalNumberOfBytesRead;
            double fileLen = file.length();
            double size = Integer.MAX_VALUE - 5;
            while(index < fileLen) {
                byte[] byteData = new byte[(int) size];
                totalNumberOfBytesRead = fis.read(byteData, 0, (int) size);
                listOfBytes.add(byteData);
                index += totalNumberOfBytesRead;
                if (fileLen - totalNumberOfBytesRead > Integer.MAX_VALUE - 5) {
                    size = Integer.MAX_VALUE - 5;
                } else {
                    size = fileLen - totalNumberOfBytesRead;
                }
            }
            fis.close();
            return listOfBytes;
        } catch (FileNotFoundException e) {
            LOGGER.error("File is not found");
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("IO Exception");
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            LOGGER.error("Increase JVM Heap size to at least 3GB");
        }
        return null;
    }

    private int getLenOfByteLeft() {
            return Integer.MAX_VALUE - 5;
    }

    public boolean isZipFile(File file) {
        boolean result = FilenameUtils.getExtension(String.valueOf(file)).equals(FileConstants.ZIP.substring(1));
        return result;
    }

    public boolean checkIfFileSizeExceedMaximumValue(File file) {
        if (file.length() > Integer.MAX_VALUE) {
            return true;
        }
        return false;
    }
}
