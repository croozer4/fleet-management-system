package com.fleet.auth_service.controller;

import com.fleet.auth_service.JwtTokenService;
import com.fleet.auth_service.model.RefreshToken;
import com.fleet.auth_service.repository.RefreshTokenRepository;
import com.fleet.auth_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fleet.auth_service.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class AuthController {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public AuthController(JwtTokenService jwtTokenService,
                          AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RefreshTokenRepository refreshTokenRepository) {
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtTokenService.generateToken(auth);
//        User user = userRepository.findByUsername(request.getUsername()).get();
        String refreshToken = "";

        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            refreshToken = jwtTokenService.generateRefreshToken(user);
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        }

        return ResponseEntity.ok(new AuthResponse(token, refreshToken));
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request){
        String requestRefreshToken = request.getRefreshToken();
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(requestRefreshToken);

        if(refreshTokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        RefreshToken refreshToken = refreshTokenOpt.get();

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        }

        User user = refreshToken.getUser();
        String token = jwtTokenService.generateTokenFromUser(user);

        return ResponseEntity.ok(new AuthResponse(token, requestRefreshToken));
    }
}

class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class AuthResponse {
    private String accessToken;
    private String refreshToken;

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}

class RefreshTokenRequest {
    private String refreshToken;

    public String getRefreshToken() { return this.refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}