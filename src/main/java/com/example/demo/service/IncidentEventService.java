package com.example.demo.service;

import com.example.demo.dto.IncidentEventResponse;
import com.example.demo.mapper.IncidentEventMapper;
import com.example.demo.repository.IncidentEventRepository;
import com.example.demo.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class IncidentEventService {

    private final IncidentEventRepository incidentEventRepository;
    private final IncidentEventMapper incidentEventMapper;
    private final IncidentRepository incidentRepository;

    public IncidentEventService(IncidentEventRepository incidentEventRepository, IncidentEventMapper incidentEventMapper, IncidentRepository incidentRepository) {
        this.incidentEventRepository = incidentEventRepository;
        this.incidentEventMapper = incidentEventMapper;
        this.incidentRepository = incidentRepository;
    }

    public List<IncidentEventResponse> getIncidentEvents(UUID uuid) {

        if (!incidentRepository.existsById(uuid)) {
            throw new NoSuchElementException("Incident not found");
        }

        var lista = incidentEventRepository.findAllByIncident_IdOrderByCreatedAtAsc(uuid);
        return incidentEventMapper.toIncidentEventResponseList(lista);
    }
}
