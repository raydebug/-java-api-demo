package com.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import jakarta.annotation.PostConstruct; // ✅ Spring Boot 3+ compatible
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    private SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    @PostConstruct
    public void init() {
        // Generate a secure key for HS512 (64 bytes or more)
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        System.out.println("[DEBUG] Generated HS512-compatible key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("[DEBUG] Invalid JWT: " + e.getMessage());
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
