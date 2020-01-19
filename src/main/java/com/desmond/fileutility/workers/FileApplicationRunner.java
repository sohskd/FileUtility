package com.desmond.fileutility.workers;

import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.service.FileProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FileApplicationRunner implements CommandLineRunner {

    private FileProcessor fileProcessor;

    public FileApplicationRunner(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void run(String... args) throws Exception {
        main(args, this.fileProcessor);
    }

    public static void main(String[] args, FileProcessor fileProcessor) {

        System.out.println(Arrays.toString(args));
        // Check args for invalid
		if (args.length == ValueConstants.THREE)
			fileProcessor.startProcessing(args);
    }
}