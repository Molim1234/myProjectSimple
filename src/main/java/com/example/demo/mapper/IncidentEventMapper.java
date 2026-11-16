package com.example.demo.mapper;

import com.example.demo.dto.IncidentEventResponse;
import com.example.demo.entity.IncidentEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncidentEventMapper {

    public IncidentEventResponse toIncidentEventResponse(IncidentEvent event) {

        return new IncidentEventResponse(
                event.getId(),
                event.getEventType(),
                event.getDescription(),
                event.getCreatedAt()
        );
    }

    public List<IncidentEventResponse> toIncidentEventResponseList(
            List<IncidentEvent> events
    ) {
        return events.stream()
                .map(this::toIncidentEventResponse)
                .toList();
    }
}
