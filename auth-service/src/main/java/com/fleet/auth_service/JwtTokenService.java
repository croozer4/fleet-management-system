package com.fleet.auth_service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
//    private static final String JWT_SECRET = "hd7S8d2jskaQ0kdpQvAwr0vqZ6jaPMe3aaaaaaaaaaaa";
private static final SecretKey JWT_KEY = Keys.hmacShaKeyFor("hd7S8d2jskaQ0kdpQvAwr0vqZ6jaPMe3".getBytes(StandardCharsets.UTF_8));
    private static final long JWT_EXPIRATION = 86400000;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(JWT_KEY)
                .compact();
    }

}
