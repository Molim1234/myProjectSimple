
package com.example.demo.mapper;

import com.example.demo.dto.IncidentResponse;
import com.example.demo.entity.Incident;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncidentMapper {

    public IncidentResponse toIncidentResponse(Incident incident) {

        return new IncidentResponse(
                incident.getId(),
                incident.getErrorCode(),
                incident.getDescription(),
                incident.getStatus(),
                incident.getCreatedAt(),
                incident.getUpdatedAt(),
                incident.getResolvedAt(),
                incident.getService().getId(),
                incident.getAlerts().stream()
                        .map(alert -> alert.getId())
                        .toList(),
                incident.getAssignedUser() != null
                        ? incident.getAssignedUser().getId()
                        : null
        );
    }

    public List<IncidentResponse> toIncidentResponseList(List<Incident> incidents) {

        return incidents.stream()
                .map(this::toIncidentResponse)
                .toList();
    }
}

