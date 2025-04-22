package com.demo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FileApiTest extends BaseApiTest {

    private String authToken;

    @BeforeEach
    void setup() {
        super.setUp();
        authToken = getAuthToken("user@example.com", "password");
    }

    @Test
    void uploadFileSuccess() {
        File testFile = new File("src/test/resources/test.txt");
        
        given(requestSpec)
            .header("Authorization", "Bearer " + authToken)
            .multiPart("file", testFile)
        .when()
            .post("/api/v1/files/upload")
        .then()
            .statusCode(200)
            .body("data.fileName", equalTo("test.txt"));
    }
} 