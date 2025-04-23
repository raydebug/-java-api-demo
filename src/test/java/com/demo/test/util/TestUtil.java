package com.demo.test.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtil {
    
    public static File createTestFile(String content) {
        try {
            Path testResourcesPath = Paths.get("src", "test", "resources");
            Files.createDirectories(testResourcesPath);
            
            Path testFile = testResourcesPath.resolve("test.txt");
            Files.write(testFile, content.getBytes());
            
            return testFile.toFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test file", e);
        }
    }
    
    public static void cleanupTestFiles() {
        try {
            Path testResourcesPath = Paths.get("src", "test", "resources", "test.txt");
            Files.deleteIfExists(testResourcesPath);
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }
} 