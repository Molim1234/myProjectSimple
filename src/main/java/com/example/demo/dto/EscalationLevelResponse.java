
package com.example.demo.dto;

import java.util.UUID;

public class EscalationLevelResponse {

    private Integer level;
    private UUID userId;
    private String firstName;
    private String lastName;
    private Integer timeoutMinutes;

    public EscalationLevelResponse() {
    }

    public EscalationLevelResponse(Integer level, UUID userId,
                                   String firstName, String lastName,
                                   Integer timeoutMinutes) {
        this.level = level;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeoutMinutes = timeoutMinutes;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getTimeoutMinutes() {
        return timeoutMinutes;
    }

    public void setTimeoutMinutes(Integer timeoutMinutes) {
        this.timeoutMinutes = timeoutMinutes;
    }
}

