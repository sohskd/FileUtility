package com.desmond.fileutility.service.filehandlers;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public interface FileSpecificHandler {

    File createFolder(File filePath, String ext) throws IOException;
}
