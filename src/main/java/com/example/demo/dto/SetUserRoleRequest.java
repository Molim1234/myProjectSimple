package com.example.demo.dto;

import com.example.demo.entity.UserRole;
import jakarta.validation.constraints.NotNull;

public class SetUserRoleRequest {
    @NotNull
    private UserRole role;

    public SetUserRoleRequest() {}
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
