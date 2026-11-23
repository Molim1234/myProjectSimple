package com.example.demo.controller;

import com.example.demo.dto.CreateUser;
import com.example.demo.dto.SetUserPasswordRequest;
import com.example.demo.dto.SetUserRoleRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid CreateUser createUser) {
        return userService.createUser(createUser);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/{uuid}")
    public UserResponse getUser(@PathVariable UUID uuid) { return userService.getUser(uuid); }

    @GetMapping("/team/{uuid}")
    public List<UserResponse> getUsersByTeam(@PathVariable UUID uuid) {
        return userService.getUsersByTeam(uuid);
    }

    @PatchMapping("/{uuid}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUserPassword(@PathVariable UUID uuid, @RequestBody @Valid SetUserPasswordRequest request) {
        userService.setUserPassword(uuid, request.getPassword());
    }

    @PatchMapping("/{uuid}/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUserRole(@PathVariable UUID uuid, @RequestBody @Valid SetUserRoleRequest request) {
        userService.setUserRole(uuid, request.getRole());
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID uuid) { userService.deleteUser(uuid); }
}
