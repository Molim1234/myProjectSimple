package com.example.demo.controller;

import com.example.demo.dto.CreateUser;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody @Valid CreateUser createUser){
        return userService.createUser(createUser);
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{uuid}")
    public UserResponse getUser(@PathVariable UUID uuid){
        return userService.getUser(uuid);
    }

    @GetMapping("/team/{uuid}")
    public List<UserResponse> getUsersByTeam(@PathVariable UUID uuid){
        return userService.getUsersByTeam(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteUser(@PathVariable UUID uuid){
        userService.deleteUser(uuid);
    }


}
