package com.demo.test;

import com.demo.test.dto.LoginRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthApiTest extends BaseApiTest {

    @Test
    void loginSuccess() {
        given(requestSpec)
            .body(new LoginRequest("admin@example.com", "password"))
        .when()
            .post("/api/v1/auth/login")
        .then()
            .statusCode(200)
            .body("data.token", notNullValue());
    }

    @Test
    void loginFailure() {
        given(requestSpec)
            .body(new LoginRequest("wrong@email.com", "wrongpass"))
        .when()
            .post("/api/v1/auth/login")
        .then()
            .statusCode(401);
    }
} 