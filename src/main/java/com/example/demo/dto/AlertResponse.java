
package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class AlertResponse {

    private UUID id;
    private String errorCode;
    private String message;
    private LocalDateTime receivedAt;
    private UUID incidentId;

    public AlertResponse() {
    }

    public AlertResponse(
            UUID id,
            String errorCode,
            String message,
            LocalDateTime receivedAt,
            UUID incidentId
    ) {
        this.id = id;
        this.errorCode = errorCode;
        this.message = message;
        this.receivedAt = receivedAt;
        this.incidentId = incidentId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public UUID getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(UUID incidentId) {
        this.incidentId = incidentId;
    }
}
