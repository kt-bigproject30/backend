package com.kt.aivle.aivleproject.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SecretKeyBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ((SecretKey)((SecretKeyBuilder) Jwts.SIG.HS256.key()).build()).getAlgorithm());
    }

    public String getUsername(String token) {
        return (String)((Claims)Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload()).get("username", String.class);
    }

    public String getRole(String token) {
        return (String)((Claims)Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload()).get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return ((Claims)Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload()).getExpiration().before(new Date());
    }

    public String createJwt(String username, String role, Long expiredMs) {
        return Jwts.builder().claim("username", username).claim("role", role).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + expiredMs)).signWith(this.secretKey).compact();
    }
}
