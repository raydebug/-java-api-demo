package com.demo.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    properties = {
        "spring.main.allow-bean-definition-overriding=true",
        "spring.jpa.hibernate.ddl-auto=create-drop"
    }
)
@ActiveProfiles("test")
public class ContextLoadTest {

    @Test
    void contextLoads() {
        // Context load test
    }
}