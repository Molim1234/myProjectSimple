package com.example.demo.service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.BootstrapRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.EscalationPolicy;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.Team;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.exception.ConflictException;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.AppUserDetails;
import com.example.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Locale;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final String bootstrapSecret;

    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                       UserRepository userRepository, TeamRepository teamRepository,
                       RefreshTokenRepository refreshTokenRepository, JwtService jwtService,
                       @Value("${app.security.bootstrap-secret:}") String bootstrapSecret) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.bootstrapSecret = bootstrapSecret;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public AuthResponse bootstrap(BootstrapRequest request) {
        if (bootstrapSecret == null || bootstrapSecret.isBlank()
                || !MessageDigest.isEqual(bootstrapSecret.getBytes(StandardCharsets.UTF_8),
                request.getBootstrapSecret().getBytes(StandardCharsets.UTF_8))) {
            throw new BadCredentialsException("Invalid bootstrap secret");
        }
        if (userRepository.existsByRole(UserRole.ADMIN)) {
            throw new ConflictException("An administrator has already been created");
        }

        String email = normalizeEmail(request.getEmail());
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);

        if (user == null) {
            String teamName = request.getTeamName().trim();
            if (teamRepository.existsByNameIgnoreCase(teamName)) {
                throw new ConflictException("A team with this name already exists");
            }
            Team team = new Team(teamName);
            team.setEscalationPolicy(new EscalationPolicy(teamName, team));
            team = teamRepository.save(team);
            user = new User(request.getFirstName().trim(), request.getLastName().trim(), email, team);
        }

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ADMIN);
        return jwtService.createTokenPair(userRepository.save(user));
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        normalizeEmail(request.getEmail()), request.getPassword()));
        User user = ((AppUserDetails) authentication.getPrincipal()).getUser();
        return jwtService.createTokenPair(user);
    }

    @Transactional
    public AuthResponse refresh(String rawRefreshToken) {
        String hash = jwtService.hashToken(rawRefreshToken);
        RefreshToken stored = refreshTokenRepository.findByTokenHashAndRevokedFalse(hash)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));
        Instant now = Instant.now();
        if (!stored.getExpiresAt().isAfter(now)
                || refreshTokenRepository.revokeIfActive(stored.getId(), now) != 1) {
            throw new BadCredentialsException("Refresh token is invalid, expired, or already used");
        }
        return jwtService.createTokenPair(stored.getUser());
    }

    @Transactional
    public void logout(String rawRefreshToken) {
        refreshTokenRepository.revokeByTokenHash(jwtService.hashToken(rawRefreshToken));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
