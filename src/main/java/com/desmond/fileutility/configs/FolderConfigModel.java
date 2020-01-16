package com.desmond.fileutility.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class FolderConfigModel {

    @Value("${folder.data.directory}")
    private String folderDataDirectory;

    @Value("${folder.processed.file.name}")
    private String processedFileName;

    @Value("#{'${folder.ignore}'.split(',')}")
    private List<String> folderIgnore;

    @Value("${folder.archivefolder.name}")
    private String archiveFolderName;

    @Value("${folder.failedfolder.name}")
    private String failedFolderName;
}
