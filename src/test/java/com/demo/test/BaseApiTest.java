package com.demo.test;

import com.demo.test.config.TestConfig;
import com.demo.test.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
public abstract class BaseApiTest {

    @LocalServerPort
    private int port;

    protected RequestSpecification requestSpec;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(port)
            .setContentType("application/json")
            .build();
    }

    protected String getAuthToken(String username, String password) {
        return RestAssured.given(requestSpec)
            .body(new LoginRequest(username, password))
            .when()
            .post("/api/v1/auth/login")
            .then()
            .statusCode(200)
            .extract()
            .path("data.token");
    }
} 