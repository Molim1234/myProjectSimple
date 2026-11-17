package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateService {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    private String description;

    private String baseUrl;

    private boolean active;

    public UpdateService() {
    }

    public UpdateService(String name, String description, String baseUrl, boolean active) {
        this.name = name;
        this.description = description;
        this.baseUrl = baseUrl;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}