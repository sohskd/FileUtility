package com.desmond.fileutility.service.filehandlers;

import com.desmond.fileutility.configs.FolderConfigModel;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class TarGzHandler implements FileSpecificHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TarGzHandler.class);

    private FolderConfigModel folderConfigModel;

    @Autowired
    public TarGzHandler(FolderConfigModel folderConfigModel) {
        this.folderConfigModel = folderConfigModel;
    }

    @Override
    public File createFolder(File filePath, String ext) throws IOException {
        LOGGER.info("> TarGzHandler createFolder");
        String folderDataDirectory = this.folderConfigModel.getFolderDataDirectory();

        String[] fileArr = filePath.getName().split("\\.");

        File archive = new File(folderDataDirectory + File.separator + filePath.getName());
        File destination = new File(folderDataDirectory + File.separator + fileArr[0]);

        Archiver archiver = ArchiverFactory.createArchiver("tar", "gz");

        archiver.extract(archive, destination);
        LOGGER.info("< TarGzHandler createFolder");
        return destination;
    }
}
