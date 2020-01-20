package com.desmond.fileutility.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

@Component
public class FileUtility {

    public FileUtility() {};

    public File createOrRetrieve(String directory) {
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
        return new File(directory);
    }

//    public File createOrRetrieve(String textPath) {
//
//        Path path = Paths.get(textPath);
//
//        if (Files.notExists(path)) {
//            try {
//                return Files.createFile(Files.createDirectories(path)).toFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return path.toFile();
//    }

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
}
