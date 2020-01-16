package com.desmond.fileutility.utils;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.exceptions.InvalidFileTypeException;
import com.desmond.fileutility.service.filehandlers.FileSpecificHandler;
import com.desmond.fileutility.service.filehandlers.TarGzHandler;
import com.desmond.fileutility.service.filehandlers.ZipHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileHandlerFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(FileHandlerFactory.class);
    private static Logger ERRORLOGGER = LoggerFactory.getLogger("errorLogger");

    private ZipHandler zipHandler;
    private TarGzHandler tarGzHandler;

    @Autowired
    public FileHandlerFactory(ZipHandler zipHandler, TarGzHandler tarGzHandler) {
        this.zipHandler = zipHandler;
        this.tarGzHandler = tarGzHandler;
    }

    public FileSpecificHandler getFileHandler(String ext) throws InvalidFileTypeException {
        LOGGER.info("> FileSpecificHandler getFileHandler");

        switch (ext) {
            case FileConstants.ZIP:
                LOGGER.info("Returning {}", FileConstants.ZIP);
                return this.zipHandler;
            case FileConstants.TAR_GZ:
                LOGGER.info("Returning {}", FileConstants.TAR_GZ);
                return this.tarGzHandler;
            default:
                ERRORLOGGER.info("Unsupported File Extension");
                throw new InvalidFileTypeException("Unsupported File Extension");
        }
    }
}
