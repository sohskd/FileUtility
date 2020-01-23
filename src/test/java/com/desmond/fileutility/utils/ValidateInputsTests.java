package com.desmond.fileutility.utils;

import com.desmond.fileutility.exceptions.InvalidArgumentsException;
import com.desmond.fileutility.exceptions.OnlyPositiveNumberException;
import com.desmond.fileutility.utils.impl.FileUtility;
import com.desmond.fileutility.utils.impl.ValidateInputs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValidateInputsTests {

    private ValidateInputs validateInputs;

    @Mock
    private FileUtility fileUtility;

    @BeforeEach
    public void setUp() {
        this.validateInputs = new ValidateInputs(fileUtility);
    }

    @DisplayName("Test Get Length Of Byte with 3 arguments and last one being the input int 10")
    @Test
    public void testValidateCompressionInputs() throws FileNotFoundException, OnlyPositiveNumberException {
        String inputDirectory = "/test/dummyIn";
        String outputDirectory = "/test/dummyOut";
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(inputDirectory)).thenReturn(true);
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(outputDirectory)).thenReturn(true);
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(inputDirectory)).thenReturn(true);
        String[] args = new String[]{inputDirectory, outputDirectory, "10"};
        assertTrue(validateInputs.validateCompressionInputs(args));
    }

    @DisplayName("Test Get Length Of Byte with 3 arguments and last one being the input float 10.72, negative values and 0")
    @Test
    public void testValidateCompressionInputsFloat() throws FileNotFoundException {
        String inputDirectory = "/test/dummyIn";
        String outputDirectory = "/test/dummyOut";
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(inputDirectory)).thenReturn(true);
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(outputDirectory)).thenReturn(true);
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(inputDirectory)).thenReturn(true);
        String[] args1 = new String[]{inputDirectory, outputDirectory, "10.72"};
        Exception exception1 = assertThrows(NumberFormatException.class, () -> {
            validateInputs.validateCompressionInputs(args1);
        });
        String expectedMessage1 = "Please input an integer for maximum byte per file";
        String actualMessage1 = exception1.getMessage();
        assertTrue(actualMessage1.contains(expectedMessage1));
    }

    @DisplayName("Test Get Length Of Byte with 3 arguments and last one being negative value")
    @Test
    public void testValidateCompressionInputsNegative() throws FileNotFoundException {
        String inputDirectory = "/test/dummyIn";
        String outputDirectory = "/test/dummyOut";
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(inputDirectory)).thenReturn(true);
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(outputDirectory)).thenReturn(true);
        Mockito.when(this.fileUtility.checkIfFileDirectoryExist(inputDirectory)).thenReturn(true);
        String[] args2 = new String[]{inputDirectory, outputDirectory, "-1"};
        Exception exception2 = assertThrows(OnlyPositiveNumberException.class, () -> {
            validateInputs.validateCompressionInputs(args2);
        });
        String[] args3 = new String[]{inputDirectory, outputDirectory, "0"};
        Exception exception3 = assertThrows(OnlyPositiveNumberException.class, () -> {
            validateInputs.validateCompressionInputs(args3);
        });
        String expectedMessage1 = "Please input an integer greater than 0";
        String actualMessage3 = exception3.getMessage();
        String actualMessage2 = exception2.getMessage();
        assertTrue(actualMessage2.contains(expectedMessage1));
        assertTrue(actualMessage3.contains(expectedMessage1));
    }

    @DisplayName("Number of arguments not being 3 or 2")
    @Test
    public void testValidateNumberOfArguments() {
        String inputDirectory = "/test/dummyIn";
        String outputDirectory = "/test/dummyOut";
        String[] args1 = new String[]{inputDirectory};
        Exception exception1 = assertThrows(InvalidArgumentsException.class, () -> {
            validateInputs.getNumberOfArguments(args1);
        });
        String expectedMessage1 = "The number of arguments is invalid";
        String actualMessage1 = exception1.getMessage();
        assertTrue(actualMessage1.contains(expectedMessage1));
        String[] args2 = new String[]{inputDirectory, outputDirectory, "-1", outputDirectory};
        Exception exception2 = assertThrows(InvalidArgumentsException.class, () -> {
            validateInputs.getNumberOfArguments(args2);
        });
        String actualMessage2 = exception2.getMessage();
        assertTrue(actualMessage2.contains(expectedMessage1));
        String[] args3 = new String[]{};
        Exception exception3 = assertThrows(InvalidArgumentsException.class, () -> {
            validateInputs.getNumberOfArguments(args3);
        });
        String actualMessage3 = exception3.getMessage();
        assertTrue(actualMessage3.contains(expectedMessage1));
    }
}
