package com.demo.test.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.DockerClientFactory;

@Configuration
public class TestContainersConfig {
    
    @PostConstruct
    public void checkDockerAvailability() {
        try {
            DockerClientFactory.instance().client().pingCmd().exec();
        } catch (Exception e) {
            throw new IllegalStateException(
                "Docker is required for tests. Please ensure Docker is running.", e);
        }
    }
} 