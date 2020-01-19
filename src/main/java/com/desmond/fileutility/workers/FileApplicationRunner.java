package com.desmond.fileutility.workers;

import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.exceptions.InvalidArgumentsException;
import com.desmond.fileutility.service.FileCompressorProcessor;
import com.desmond.fileutility.service.FileDecompressorProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FileApplicationRunner implements CommandLineRunner {

    private FileCompressorProcessor fileCompressorProcessor;
    private FileDecompressorProcessor fileDecompressorProcessor;

    @Autowired
    public FileApplicationRunner(FileCompressorProcessor fileCompressorProcessor, FileDecompressorProcessor fileDecompressorProcessor) {
        this.fileCompressorProcessor = fileCompressorProcessor;
        this.fileDecompressorProcessor = fileDecompressorProcessor;
    }

    @Override
    public void run(String... args) throws Exception {
        main(args, this.fileCompressorProcessor, this.fileDecompressorProcessor);
    }

    public static void main(String[] args, FileCompressorProcessor fileCompressorProcessor, FileDecompressorProcessor fileDecompressorProcessor) {

        switch (args.length) {
            case ValueConstants.THREE:
                fileCompressorProcessor.startProcessing(args);
                break;
            case ValueConstants.TWO:
                fileDecompressorProcessor.startProcessing(args);
                break;
            default:
                try {
                    throw new InvalidArgumentsException("Invalid arguments error");
                } catch (InvalidArgumentsException e) {
                    e.printStackTrace();
                }
        }
    }
}