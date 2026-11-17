
package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class AssignIncident {

    @NotNull
    private UUID userId;

    public AssignIncident() {
    }

    public AssignIncident(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}

