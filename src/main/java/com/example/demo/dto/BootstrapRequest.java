package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BootstrapRequest {
    @NotBlank @Size(min = 2, max = 100)
    private String teamName;
    @NotBlank @Size(max = 50)
    private String firstName;
    @NotBlank @Size(max = 50)
    private String lastName;
    @NotBlank @Email @Size(max = 150)
    private String email;
    @NotBlank @Size(min = 12, max = 72)
    private String password;
    @NotBlank
    private String bootstrapSecret;

    public BootstrapRequest() {}
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getBootstrapSecret() { return bootstrapSecret; }
    public void setBootstrapSecret(String bootstrapSecret) { this.bootstrapSecret = bootstrapSecret; }
}
