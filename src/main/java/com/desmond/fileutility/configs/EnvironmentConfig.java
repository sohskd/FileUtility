package com.desmond.fileutility.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EnvironmentConfig {

    @Value("${environment.working.directory}")
    private String environmentWorkingDirectory;
}
