package com.fleet.auth_service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.util.Date;

@Service
public class JwtTokenService {
//    private static final String JWT_SECRET = "hd7S8d2jskaQ0kdpQvAwr0vqZ6jaPMe3aaaaaaaaaaaa";
private static final SecretKey JWT_KEY = Keys.hmacShaKeyFor("hd7S8d2jskaQ0kdpQvAwr0vqZ6jaPMe3".getBytes(StandardCharsets.UTF_8));
    private static final long JWT_EXPIRATION = 86400000;

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION)).signWith(JWT_KEY).compact();
    }

}
