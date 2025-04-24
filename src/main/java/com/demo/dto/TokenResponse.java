package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("type")
    private String type;

    public TokenResponse(String token) {
        this.token = token;
        this.type = "Bearer";
    }
} 