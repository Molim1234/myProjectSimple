package com.example.demo.dto;

import com.example.demo.entity.UserRole;
import java.util.UUID;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresInSeconds;
    private UUID userId;
    private String email;
    private UserRole role;
    private UUID teamId;

    public AuthResponse() {}
    public AuthResponse(String accessToken, String refreshToken, String tokenType, long expiresInSeconds,
                        UUID userId, String email, UserRole role, UUID teamId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresInSeconds = expiresInSeconds;
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.teamId = teamId;
    }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public String getTokenType() { return tokenType; }
    public long getExpiresInSeconds() { return expiresInSeconds; }
    public UUID getUserId() { return userId; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
    public UUID getTeamId() { return teamId; }
}
