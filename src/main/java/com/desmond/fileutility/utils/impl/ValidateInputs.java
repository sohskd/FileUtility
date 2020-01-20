package com.desmond.fileutility.utils.impl;

import com.desmond.fileutility.exceptions.OnlyPositiveNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class ValidateInputs {

    private FileUtility fileUtility;

    @Autowired
    public ValidateInputs(FileUtility fileUtility) {
        this.fileUtility = fileUtility;
    }

    public boolean validateCompressionInputs(String[] args) throws FileNotFoundException, OnlyPositiveNumberException {

        String inputDirectory = args[0];
        String outputDirectory = args[1];
        String maximumMegaBytes = args[2];

        boolean isInput = checkFileDirectoryExist(inputDirectory);
        boolean isOutput = checkFileDirectoryExist(outputDirectory);
        boolean isInt = checkIfInt(maximumMegaBytes);

        return isInput && isOutput && isInt;
    }

    public boolean validateDecompressionInputs(String[] args) throws FileNotFoundException {

        String inputDirectory = args[0];
        String outputDirectory = args[1];
        return checkFileDirectoryExist(inputDirectory) && checkFileDirectoryExist(outputDirectory);
    }

    private boolean checkFileDirectoryExist(String inputDirectory) throws FileNotFoundException {
        boolean result = this.fileUtility.checkIfFileDirectoryExist(inputDirectory);
        return result;
    }

    private boolean checkIfInt(String maximumMegaBytes) throws NumberFormatException, OnlyPositiveNumberException {
        int i;
        try {
            i = Integer.parseInt(maximumMegaBytes);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please input an integer for maximum byte per file");
        }

        if (i <= 0)
            throw new OnlyPositiveNumberException("Please input an integer greater than 0");

        return true;
    }
}
