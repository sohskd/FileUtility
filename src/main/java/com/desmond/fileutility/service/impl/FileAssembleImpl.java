package com.desmond.fileutility.service.impl;

import com.desmond.fileutility.service.FileAssemble;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileAssembleImpl implements FileAssemble {

    List<byte[]> bytesList;
    private String name;
    boolean finished = false;
    private FileOutputStream fileOutputStream;

    public FileAssembleImpl() {};

    public FileAssembleImpl(String name) {
        bytesList = new ArrayList<byte[]>();
        this.name = name;
    }

    public void addBytes(byte[] bytes) {
        if (bytes.length < 512)
            finished = true;
        bytesList.add(bytes);
    }

    public boolean isFinished() {
        return finished;
    }

    public void createFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(name);
        for (byte[] data: bytesList)
            fos.write(data);
    }

    private void writeBytesToFile(byte[] bytes) {
        try {
            fileOutputStream = new FileOutputStream(name);
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<byte[]> getBytesList() {
        return bytesList;
    }

    public void setBytesList(List<byte[]> bytesList) {
        this.bytesList = bytesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public FileOutputStream getFileOutputStream() {
        return fileOutputStream;
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }
}
