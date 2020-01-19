package com.desmond.fileutility.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FileValidatorTests {

    private FileValidator fileValidator;

    @Autowired
    public FileValidatorTests(FileValidator fileValidator) {
        this.fileValidator = fileValidator;
    }

    @DisplayName("Test Get Length Of Byte")
    @Test
    public void testGetLengthOfByte() {
        int totalByteLengthOfFile = 4;
        int len = 2;
        int off = 0;

        assertEquals(2, this.fileValidator.getLengthOfByte(totalByteLengthOfFile, off, len));
    }

    @DisplayName("Test Get Length Of Byte when offset and len exceeds totalByteLengthOfFile")
    @Test
    public void testGetLengthOfByteLenAndOffsetExceeds() {
        int totalByteLengthOfFile = 4;
        int len = 2;
        int off = 3;

        assertEquals(1, this.fileValidator.getLengthOfByte(totalByteLengthOfFile, off, len));
    }
}
