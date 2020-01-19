package com.desmond.fileutility.service;

import com.desmond.fileutility.constants.FileConstants;
import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.model.FileInProcess;
import com.desmond.fileutility.service.impl.ZipServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ZipServiceImplTests {

    private ZipService zipService;

    @MockBean
    private FileInProcess fileInProcess;

    @Autowired
    public ZipServiceImplTests(ZipService zipService) {
        this.zipService = zipService;
    }

    @DisplayName("Test get full file name")
    @Test
    public void testGetFullFileName() {

        String expected = "/Users/TestFileFolders/TestOutput/test6.txt.zip";
        String fileName = "test6.txt";
        String outputPath = "/Users/TestFileFolders/TestOutput";

        when(this.fileInProcess.getFileOutput()).thenReturn(outputPath);

        assertEquals(expected, this.zipService.getFullFileName(fileName, FileConstants.ZIP));
    }
}
