package com.desmond.fileutility;

import com.desmond.fileutility.constants.ValueConstants;
import com.desmond.fileutility.service.FileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class FileutilityApplication {

	FileProcessor fileProcessor;

	@Autowired
	public FileutilityApplication(FileProcessor fileProcessor) {
		this.fileProcessor = fileProcessor;
	}

	public static void main(String[] args) {
		SpringApplication.run(FileutilityApplication.class, args);
	}
}
