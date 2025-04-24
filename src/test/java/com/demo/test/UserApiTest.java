package com.demo.test;

import com.demo.test.dto.UserDto;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseApiTest {

    @Test
    void createUserSuccess() {
        given(requestSpec)
            .header("Authorization", "Bearer " + authToken)
            .body(new UserDto("test@example.com", "Test", "User", "password123"))
        .when()
            .post("/api/v1/users")
        .then()
            .statusCode(201)
            .body("data.email", equalTo("test@example.com"))
            .body("data.firstName", equalTo("Test"))
            .body("data.lastName", equalTo("User"));
    }

    @Test
    void getUsersList() {
        given(requestSpec)
            .header("Authorization", "Bearer " + authToken)
        .when()
            .get("/api/v1/users")
        .then()
            .statusCode(200)
            .body("data.content", hasSize(greaterThan(0)))
            .body("data.content[0].email", notNullValue())
            .body("data.content[0].firstName", notNullValue())
            .body("data.content[0].lastName", notNullValue());
    }
} 