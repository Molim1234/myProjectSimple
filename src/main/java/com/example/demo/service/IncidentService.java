package com.example.demo.service;

import com.example.demo.dto.IncidentResponse;
import com.example.demo.entity.IncidentStatus;
import com.example.demo.exception.ConflictException;
import com.example.demo.mapper.IncidentMapper;
import com.example.demo.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    public IncidentService(IncidentRepository incidentRepository, IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
    }


    public List<IncidentResponse> getAllIncidents() {
        var list = incidentRepository.findAll();
        return incidentMapper.toIncidentResponseList(list);
    }


    public IncidentResponse getIncident(UUID uuid) {
        var incident = incidentRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Incident not found"));
        return incidentMapper.toIncidentResponse(incident);
    }


    public IncidentResponse setAcknowledge(UUID uuid) {
        var incident = incidentRepository.findById(uuid).orElseThrow(()->new NoSuchElementException("Incident not found"));
        if(incident.getStatus() == IncidentStatus.RESOLVED){
            throw new ConflictException("Resolved incident cannot be acknowledged");
        }
        incident.setStatus(IncidentStatus.ACKNOWLEDGED);
        var savedIncident = incidentRepository.save(incident);
        return incidentMapper.toIncidentResponse(savedIncident);
    }


    public IncidentResponse setResolved(UUID uuid) {
        var incident = incidentRepository.findById(uuid).orElseThrow(()->new NoSuchElementException("Incident not found"));
        incident.setStatus(IncidentStatus.RESOLVED);
        incident.setResolvedAt(LocalDateTime.now());
        var savedIncident = incidentRepository.save(incident);
        return incidentMapper.toIncidentResponse(savedIncident);
    }
}
