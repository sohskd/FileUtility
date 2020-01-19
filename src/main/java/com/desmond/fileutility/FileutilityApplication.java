package com.desmond.fileutility;

import com.desmond.fileutility.service.FileCompressorProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileutilityApplication {

	FileCompressorProcessor fileCompressorProcessor;

	@Autowired
	public FileutilityApplication(FileCompressorProcessor fileCompressorProcessor) {
		this.fileCompressorProcessor = fileCompressorProcessor;
	}

	public static void main(String[] args) {
		SpringApplication.run(FileutilityApplication.class, args);
	}
}
