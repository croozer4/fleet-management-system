package com.fleet.auth_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    private UUID id;
    private String token;
    private LocalDateTime expiryDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UUID getId() { return this.id; }

    public String getToken() { return this.token; }
    public void setToken(String token) { this.token = token; }

    public LocalDateTime getExpiryDate() { return this.expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }
}
