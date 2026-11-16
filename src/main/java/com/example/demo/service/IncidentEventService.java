package com.example.demo.service;

import com.example.demo.dto.IncidentEventResponse;
import com.example.demo.mapper.IncidentEventMapper;
import com.example.demo.repository.IncidentEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IncidentEventService {

    private final IncidentEventRepository incidentEventRepository;
    private final IncidentEventMapper incidentEventMapper;

    public IncidentEventService(IncidentEventRepository incidentEventRepository, IncidentEventMapper incidentEventMapper) {
        this.incidentEventRepository = incidentEventRepository;
        this.incidentEventMapper = incidentEventMapper;
    }

    public List<IncidentEventResponse> getIncidentEvents(UUID uuid) {
        var lista = incidentEventRepository.findAllByIncident_IdOrderByCreatedAtAsc(uuid);
        return incidentEventMapper.toIncidentEventResponseList(lista);
    }
}
