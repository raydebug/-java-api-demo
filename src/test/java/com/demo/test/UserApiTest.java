package com.demo.test;

import com.demo.test.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseApiTest {

    private String authToken;

    @BeforeEach
    void setup() {
        super.setUp();
        authToken = getAuthToken();
    }

    @Test
    void createUserSuccess() {
        given(requestSpec)
            .header("Authorization", "Bearer " + authToken)
            .body(new UserDto("test@example.com", "Test", "User"))
        .when()
            .post("/api/v1/users")
        .then()
            .statusCode(201)
            .body("data.email", equalTo("test@example.com"));
    }

    @Test
    void getUsersList() {
        given(requestSpec)
            .header("Authorization", "Bearer " + authToken)
        .when()
            .get("/api/v1/users")
        .then()
            .statusCode(200)
            .body("data", hasSize(greaterThan(0)));
    }
} 