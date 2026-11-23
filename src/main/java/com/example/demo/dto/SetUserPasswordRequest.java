package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SetUserPasswordRequest {
    @NotBlank @Size(min = 12, max = 72)
    private String password;
    public SetUserPasswordRequest() {}
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
