
package com.example.demo.dto;

import com.example.demo.entity.IncidentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class IncidentResponse {

    private UUID id;
    private String errorCode;
    private String description;
    private IncidentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
    private UUID serviceId;
    private List<UUID> alertIds;

    public IncidentResponse() {
    }

    public IncidentResponse(
            UUID id,
            String errorCode,
            String description,
            IncidentStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime resolvedAt,
            UUID serviceId,
            List<UUID> alertIds
    ) {
        this.id = id;
        this.errorCode = errorCode;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resolvedAt = resolvedAt;
        this.serviceId = serviceId;
        this.alertIds = alertIds;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public List<UUID> getAlertIds() {
        return alertIds;
    }

    public void setAlertIds(List<UUID> alertIds) {
        this.alertIds = alertIds;
    }
}

