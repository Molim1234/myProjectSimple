
package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public class AddEscalationLevelRequest {

    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Timeout minutes is required")
    @PositiveOrZero(message = "Timeout minutes must be zero or greater")
    private Integer timeoutMinutes;

    public AddEscalationLevelRequest() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getTimeoutMinutes() {
        return timeoutMinutes;
    }

    public void setTimeoutMinutes(Integer timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
    }
}
