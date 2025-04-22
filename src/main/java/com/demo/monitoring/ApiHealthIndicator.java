package com.demo.monitoring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            // Add your health check logic here
            return Health.up()
                    .withDetail("app", "Running")
                    .withDetail("error", "None")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("app", "Not Running")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
} 