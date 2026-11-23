package com.example.demo.dto;

import com.example.demo.entity.UserRole;
import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private UUID teamId;
    private String teamName;
    private UserRole role;

    public UserResponse() {}
    public UserResponse(UUID id, String firstName, String lastName, String email,
                        UUID teamId, String teamName, UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.teamId = teamId;
        this.teamName = teamName;
        this.role = role;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UUID getTeamId() { return teamId; }
    public void setTeamId(UUID teamId) { this.teamId = teamId; }
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
