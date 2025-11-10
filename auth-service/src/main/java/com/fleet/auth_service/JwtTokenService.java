package com.fleet.auth_service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.Signature;
import java.util.Date;
@Service
public class JwtTokenService {
    private static final String JWT_SECRET = "twojTajnySekretJWT12345678901234";
    private static final long JWT_EXPIRATION = 86400000;

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION)).signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
    }

}
