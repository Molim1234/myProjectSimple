package com.example.demo.service;

import com.example.demo.dto.CreateUser;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.UserRole;
import com.example.demo.exception.ConflictException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, TeamRepository teamRepository,
                       PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public UserResponse createUser(CreateUser createUser) {
        String email = createUser.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ConflictException("User with this email already exists");
        }
        var team = teamRepository.findById(createUser.getTeamId())
                .orElseThrow(() -> new NoSuchElementException("Team not found"));
        var user = userMapper.toUser(createUser, team);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(createUser.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public void setUserPassword(UUID userId, String rawPassword) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        refreshTokenRepository.revokeAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(UUID uuid) {
        var user = userRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByTeam(UUID teamUuid) {
        teamRepository.findById(teamUuid).orElseThrow(() -> new NoSuchElementException("Team not found"));
        return userMapper.toUserResponseList(userRepository.findAllByTeam_Id(teamUuid));
    }

    @Transactional
    public void deleteUser(UUID uuid) {
        var user = userRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (user.getRole() == UserRole.ADMIN && userRepository.countByRole(UserRole.ADMIN) <= 1) {
            throw new ConflictException("The last administrator cannot be deleted");
        }
        refreshTokenRepository.deleteAllByUser_Id(uuid);
        userRepository.delete(user);
    }
}
