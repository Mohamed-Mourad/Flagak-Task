package com.flagak.task_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtil {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String SECRET_KEY = "c2VjdXJlLXJhbmRvbS1rZXktMTIzNDU2Nzg5MA==";

    private final SecretKey key;

    public JwtUtil() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        // Token validity: 24 hours (in milliseconds)
        long expirationTime = 86400000;
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidToken(String token) {
        try {
            getClaimsFromToken(token);  // This will throw an exception if the token is invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
