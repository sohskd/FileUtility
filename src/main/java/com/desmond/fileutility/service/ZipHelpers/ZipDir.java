//package com.desmond.fileutility.service.ZipHelpers;
//
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.nio.file.FileVisitResult;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.SimpleFileVisitor;
//import java.nio.file.attribute.BasicFileAttributes;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//@Service
//public class ZipDir extends SimpleFileVisitor<Path> {
//
//    private static ZipOutputStream zos;
//
//    private Path sourceDir;
//
//    public ZipDir(Path sourceDir) {
//        this.sourceDir = sourceDir;
//    }
//
//    @Override
//    public FileVisitResult visitFile(Path file,
//                                     BasicFileAttributes attributes) {
//
//        try {
//            Path targetFile = sourceDir.relativize(file);
//
//            zos.putNextEntry(new ZipEntry(targetFile.toString()));
//
//            byte[] bytes = Files.readAllBytes(file);
//            zos.write(bytes, 0, bytes.length);
//            zos.closeEntry();
//
//        } catch (IOException ex) {
//            System.err.println(ex);
//        }
//
//        return FileVisitResult.CONTINUE;
//    }
//}
