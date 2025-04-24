package com.demo.test;

import static io.restassured.RestAssured.given;
import com.demo.dto.ApiResponse;
import com.demo.dto.TokenResponse;
import com.demo.model.User;
import com.demo.model.UserRole;
import com.demo.repository.UserRepository;
import com.demo.security.JwtTokenProvider;
import com.demo.test.config.TestConfig;
import com.demo.test.util.TestUsers;
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

    @Autowired
    protected UserRepository userRepository;

    protected RequestSpecification requestSpec;
    
    protected String authToken;

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

        // Get auth token for the admin user from data.sql
        authToken = getAuthToken();
    }

    protected String getAuthToken() {
        try {
            return given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", 
                    "admin@example.com", "password"))
            .when()
                .post("/api/v1/auth/login")
            .then()
                .statusCode(200)
                .extract()
                .path("data.token");
        } catch (Exception e) {
            System.err.println("Failed to get auth token: " + e.getMessage());
            throw e;
        }
    }

    protected String obtainAuthToken() {
        try {
            return given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", 
                    TestUsers.EMAIL, TestUsers.PASSWORD))
            .when()
                .post("/api/v1/auth/login")
            .then()
                .statusCode(200)
                .extract()
                .path("data.token");
        } catch (Exception e) {
            System.err.println("Failed to obtain auth token: " + e.getMessage());
            throw e;
        }
    }
} 