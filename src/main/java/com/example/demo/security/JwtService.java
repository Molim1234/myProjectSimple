package com.example.demo.security;

import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.List;

@Service
public class JwtService {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final JwtEncoder jwtEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long accessTokenMinutes;
    private final long refreshTokenDays;

    public JwtService(JwtEncoder jwtEncoder, RefreshTokenRepository refreshTokenRepository,
                      @Value("${app.security.access-token-minutes:15}") long accessTokenMinutes,
                      @Value("${app.security.refresh-token-days:7}") long refreshTokenDays) {
        this.jwtEncoder = jwtEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenMinutes = accessTokenMinutes;
        this.refreshTokenDays = refreshTokenDays;
    }

    public AuthResponse createTokenPair(User user) {
        Instant now = Instant.now();
        Instant accessExpiresAt = now.plus(Duration.ofMinutes(accessTokenMinutes));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("incident-management-platform")
                .issuedAt(now)
                .expiresAt(accessExpiresAt)
                .subject(user.getEmail())
                .claim("userId", user.getId().toString())
                .claim("teamId", user.getTeam().getId().toString())
                .claim("roles", List.of(user.getRole().name()))
                .build();

        JwsHeader headers = JwsHeader.with(MacAlgorithm.HS256).build();
        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(headers, claims)).getTokenValue();

        byte[] randomBytes = new byte[64];
        SECURE_RANDOM.nextBytes(randomBytes);
        String rawRefreshToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        Instant refreshExpiresAt = now.plus(Duration.ofDays(refreshTokenDays));
        refreshTokenRepository.save(new RefreshToken(hashToken(rawRefreshToken), user, refreshExpiresAt));

        return new AuthResponse(accessToken, rawRefreshToken, "Bearer",
                Duration.between(now, accessExpiresAt).toSeconds(), user.getId(),
                user.getEmail(), user.getRole(), user.getTeam().getId());
    }

    public String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(token.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is not available", exception);
        }
    }
}
