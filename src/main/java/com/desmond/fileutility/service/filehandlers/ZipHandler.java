package com.desmond.fileutility.service.filehandlers;

import com.desmond.fileutility.configs.FolderConfigModel;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ZipHandler implements FileSpecificHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipHandler.class);

    private FolderConfigModel folderConfigModel;

    @Autowired
    public ZipHandler(FolderConfigModel folderConfigModel) {
        this.folderConfigModel = folderConfigModel;
    }

    @Override
    public File createFolder(File filePath, String ext) {
        LOGGER.info("> ZipHandler createFolder");
        ZipFile zipFile;
        String folderDataDirectory = this.folderConfigModel.getFolderDataDirectory();

        try {
            zipFile = new ZipFile(filePath);
            zipFile.extractAll(folderDataDirectory + File.separator + filePath.getName().replace("." + ext, ""));
        } catch (ZipException e) {
            e.printStackTrace();
        }

        String createdFolder = folderDataDirectory + File.separator + filePath.getName().replace("." + ext, "");

        LOGGER.info("createdFolder: {}", createdFolder);
        LOGGER.info("< ZipHandler createFolder");
        return new File(createdFolder);
    }
}