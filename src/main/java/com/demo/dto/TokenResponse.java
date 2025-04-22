package com.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private String type;

    public TokenResponse(String token) {
        this.token = token;
        this.type = "Bearer";
    }
} 