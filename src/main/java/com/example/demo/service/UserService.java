package com.example.demo.service;

import com.example.demo.dto.CreateUser;
import com.example.demo.dto.UserResponse;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TeamRepository teamRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.teamRepository = teamRepository;
    }

    public UserResponse createUser(@Valid CreateUser createUser) {
        var team = teamRepository.findById(createUser.getTeamId()).orElseThrow(()-> new NoSuchElementException("Team not found"));
        var user = userMapper.toUser(createUser, team);
        var savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        var list = userRepository.findAll();
        return userMapper.toUserResponseList(list);
    }

    public UserResponse getUser(UUID uuid) {
        var user = userRepository.findById(uuid).orElseThrow(()->new NoSuchElementException("User not found"));
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsersByTeam(UUID TeamUuid) {
        var list = userRepository.findAllByTeam_Id(TeamUuid);
        return userMapper.toUserResponseList(list);
    }

    public void deleteUser(UUID uuid) {
        var user = userRepository.findById(uuid).orElseThrow(()->new NoSuchElementException("User not found"));
        userRepository.delete(user);
    }
}
