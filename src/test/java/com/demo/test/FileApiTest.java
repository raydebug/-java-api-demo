package com.demo.test;

import com.demo.repository.FileRepository;
import com.demo.test.config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(
    classes = {TestApplication.class, TestConfig.class},
    webEnvironment = RANDOM_PORT,
    properties = {
        "spring.main.allow-bean-definition-overriding=true",
        "spring.servlet.multipart.max-file-size=10MB",
        "spring.servlet.multipart.max-request-size=10MB",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.security.user.name=test@example.com",
        "spring.security.user.password=test"
    }
)
class FileApiTest extends BaseApiTest {

    @Autowired
    private FileRepository fileRepository;
    
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    private File testFile;
    
    @BeforeEach
    void setupTest() {
        super.setUp();
        
        // Clean up file repository
        fileRepository.deleteAll();
        
        // Create upload directory
        try {
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            fail("Could not create upload directory: " + e.getMessage());
        }
        
        // Create test file
        testFile = new File("src/test/resources/test.txt");
        if (!testFile.exists()) {
            try {
                testFile.getParentFile().mkdirs();
                Files.write(testFile.toPath(), "Test file content".getBytes());
            } catch (IOException e) {
                fail("Could not create test file");
            }
        }
    }
    
    @AfterEach
    void cleanup() {
        fileRepository.deleteAll();
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
        // Clean up upload directory
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (Files.exists(uploadPath)) {
                Files.walk(uploadPath)
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            // Ignore cleanup errors
                        }
                    });
            }
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }

    @Test
    void uploadFileSuccess() {
        assumeTrue(testFile.exists(), "Test file must exist");

        String token = getAuthToken();

        given()
                .header("Authorization", "Bearer " + token)
                .multiPart("file", testFile) // Automatically sets multipart/form-data
                .when()
                .post("/api/v1/files/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.fileName", equalTo("test.txt"))
                .body("data.fileSize", greaterThan(0));
    }
} 