package com.demo.test;

import com.demo.repository.FileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class FileApiTest extends BaseApiTest {

    @Autowired
    private FileRepository fileRepository;
    
    private File testFile;
    
    @BeforeEach
    void setupTest() {
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
    }

    @Test
    void uploadFileSuccess() {
        assumeTrue(testFile.exists(), "Test file must exist");
        
        given(requestSpec)
            .header("Authorization", "Bearer " + getAuthToken())
            .multiPart("file", testFile)
        .when()
            .post("/api/v1/files/upload")
        .then()
            .statusCode(200)
            .body("data.fileName", equalTo("test.txt"))
            .body("data.fileSize", greaterThan(0));
    }
} 