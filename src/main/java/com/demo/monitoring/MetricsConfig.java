package com.demo.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter requestCounter(MeterRegistry registry) {
        return Counter.builder("api.requests.total")
                .description("Total API requests")
                .register(registry);
    }
} 