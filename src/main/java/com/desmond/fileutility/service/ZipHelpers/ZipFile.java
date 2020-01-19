package com.desmond.fileutility.service.ZipHelpers;

import org.springframework.stereotype.Component;
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
public class ZipFile {

    public ZipFile() {

    }

    public void zipFile(List<File> listOfFiles) {
        for (int i = 0; i < listOfFiles.size(); i++) {
            try {
                File file = listOfFiles.get(i);
                String zipFileName = file.getName().concat(".zip");

                FileOutputStream fos = new FileOutputStream(zipFileName);
                ZipOutputStream zos = new ZipOutputStream(fos);

                zos.putNextEntry(new ZipEntry(file.getName()));

                byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
                zos.close();

            } catch (FileNotFoundException ex) {
                System.err.format("The file does not exist");
            } catch (IOException ex) {
                System.err.println("I/O error: " + ex);
            }
        }
    }
}
