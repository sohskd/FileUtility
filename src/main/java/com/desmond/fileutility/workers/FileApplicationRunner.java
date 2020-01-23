package com.desmond.fileutility.workers;

import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.exceptions.InvalidArgumentsException;
import com.desmond.fileutility.exceptions.OnlyPositiveNumberException;
import com.desmond.fileutility.service.FileCompressorProcessor;
import com.desmond.fileutility.service.FileDecompressorProcessor;
import com.desmond.fileutility.utils.impl.ValidateInputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class FileApplicationRunner implements CommandLineRunner {

    private static Logger LOGGER = LoggerFactory.getLogger(FileApplicationRunner.class);

    private FileCompressorProcessor fileCompressorProcessor;
    private FileDecompressorProcessor fileDecompressorProcessor;
    private ValidateInputs validateInputs;

    @Autowired
    public FileApplicationRunner(FileCompressorProcessor fileCompressorProcessor, FileDecompressorProcessor fileDecompressorProcessor, ValidateInputs validateInputs) {
        this.fileCompressorProcessor = fileCompressorProcessor;
        this.fileDecompressorProcessor = fileDecompressorProcessor;
        this.validateInputs = validateInputs;
    }

    @Override
    public void run(String... args) {
        main(args, this.fileCompressorProcessor, this.fileDecompressorProcessor, this.validateInputs);
    }

    public static void main(String[] args, FileCompressorProcessor fileCompressorProcessor, FileDecompressorProcessor fileDecompressorProcessor, ValidateInputs validateInputs) {
        try {
            switch (validateInputs.getNumberOfArguments(args)) {
                case ValueConstants.THREE:
                    validateInputs.validateCompressionInputs(args);
                    fileCompressorProcessor.startProcessing(args);
                    break;
                case ValueConstants.TWO:
                    validateInputs.validateDecompressionInputs(args);
                    fileDecompressorProcessor.startProcessing(args);
                    break;
            }
        } catch (FileNotFoundException | NumberFormatException | InvalidArgumentsException | OnlyPositiveNumberException e) {
            LOGGER.error(e.getMessage());
        }
    }
}