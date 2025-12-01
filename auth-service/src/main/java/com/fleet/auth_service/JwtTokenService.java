package com.fleet.auth_service;

import com.fleet.auth_service.model.RefreshToken;
import com.fleet.auth_service.model.User;
import com.fleet.auth_service.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class JwtTokenService {
//    private static final String JWT_SECRET = "hd7S8d2jskaQ0kdpQvAwr0vqZ6jaPMe3aaaaaaaaaaaa";
    private static final SecretKey JWT_KEY = Keys.hmacShaKeyFor("hd7S8d2jskaQ0kdpQvAwr0vqZ6jaPMe3".getBytes(StandardCharsets.UTF_8));
//    private static final long JWT_EXPIRATION = 900000; // 15 min
    private static final long JWT_EXPIRATION = 60000; // 1 min
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }


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

    public String generateTokenFromUser(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", List.of(user.getRole()))  // jeśli masz tylko pojedynczą rolę
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(JWT_KEY)
                .compact();
    }


    public String generateRefreshToken(User user) {
        String refreshToken = UUID.randomUUID().toString();
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setUser(user);
        tokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(tokenEntity);
        return refreshToken;
    }

}
