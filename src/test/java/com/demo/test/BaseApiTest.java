package com.demo.test;

import static io.restassured.RestAssured.given;
import com.demo.dto.ApiResponse;
import com.demo.dto.TokenResponse;
import com.demo.security.JwtTokenProvider;
import com.demo.test.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import io.restassured.http.ContentType;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {TestApplication.class, TestConfig.class}
)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class BaseApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    protected RequestSpecification requestSpec;
    
    private String authToken;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(port)
            .setContentType("application/json")
            .log(LogDetail.ALL)
            .build();
    }

    private void initializeAuthToken() {
        try {
            if (authToken == null) {
                authToken = obtainAuthToken();
            }
        } catch (Exception e) {
            System.err.println("Auth token initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected String getAuthToken() {
        String token = given()
            .contentType(ContentType.JSON)
            .body("{\"email\":\"test@example.com\",\"password\":\"test\"}")
        .when()
            .post("/api/v1/auth/login")
        .then()
            .statusCode(200)
            .extract()
            .path("data.token");
        
        return token;
    }

    protected String obtainAuthToken() {
        String token = given()
            .contentType(ContentType.JSON)
            .body("{\"email\":\"test@example.com\",\"password\":\"test\"}")
        .when()
            .post("/api/v1/auth/login")
        .then()
            .statusCode(200)
            .extract()
            .path("data.token");
            
        return token;
    }
} 