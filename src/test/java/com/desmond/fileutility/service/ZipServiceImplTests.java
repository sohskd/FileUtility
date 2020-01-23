package com.desmond.fileutility.service;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.impl.ZipServiceImpl;
import com.desmond.fileutility.utils.impl.FileUtility;
import com.desmond.fileutility.utils.impl.FileValidator;
import com.desmond.fileutility.utils.impl.PathNameUtility;
import com.desmond.fileutility.utils.impl.ValidateInputs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ZipServiceImplTests {

    private ZipService zipService;

    @Mock
    private FileInProcess fileInProcess;

    @Mock
    private DirectoryProcessor directoryProcessor;

    @Mock
    private FileValidator fileValidator;

    @Mock
    private FileUtility fileUtility;

    @Mock
    private PathNameUtility pathNameUtility;

    @BeforeEach
    public void setUp() {
        this.zipService = new ZipServiceImpl(fileInProcess, directoryProcessor, fileValidator, fileUtility, pathNameUtility);
    }

    @DisplayName("Test get full file name")
    @Test
    public void testGetFullFileName() {

        String expected = "/Users/TestFileFolders/TestOutput/test6.txt.zip";
        String fileName = "test6.txt";
        String outputPath = "/Users/TestFileFolders/TestOutput";

        when(this.fileInProcess.getFileOutput()).thenReturn(outputPath);

//        assertEquals(expected, this.zipService.getFullFileName(fileName, FileConstants.ZIP));
    }
}
