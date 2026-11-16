package com.example.demo.dto;

import com.example.demo.entity.IncidentEventType;

import java.time.LocalDateTime;
import java.util.UUID;

public class IncidentEventResponse {

    private UUID id;
    private IncidentEventType eventType;
    private String description;
    private LocalDateTime createdAt;

    public IncidentEventResponse() {
    }

    public IncidentEventResponse(
            UUID id,
            IncidentEventType eventType,
            String description,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.eventType = eventType;
        this.description = description;
        this.createdAt = createdAt;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public IncidentEventType getEventType() {
        return eventType;
    }

    public void setEventType(IncidentEventType eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}