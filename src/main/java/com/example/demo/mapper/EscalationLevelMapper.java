
package com.example.demo.mapper;

import com.example.demo.dto.EscalationLevelResponse;
import com.example.demo.entity.EscalationLevel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EscalationLevelMapper {

    public EscalationLevelResponse toEscalationLevelResponse(EscalationLevel escalationLevel) {
        return new EscalationLevelResponse(
                escalationLevel.getLevel(),
                escalationLevel.getUser().getId(),
                escalationLevel.getUser().getFirstName(),
                escalationLevel.getUser().getLastName(),
                escalationLevel.getTimeoutMinutes()
        );
    }

    public List<EscalationLevelResponse> toEscalationLevelResponseList(
            List<EscalationLevel> escalationLevels) {

        return escalationLevels.stream()
                .map(this::toEscalationLevelResponse)
                .toList();
    }
}

