package com.example.demo.service;

import com.example.demo.dto.IncidentResponse;
import com.example.demo.entity.IncidentEvent;
import com.example.demo.entity.IncidentEventType;
import com.example.demo.entity.IncidentStatus;
import com.example.demo.exception.ConflictException;
import com.example.demo.mapper.IncidentMapper;
import com.example.demo.repository.IncidentEventRepository;
import com.example.demo.repository.IncidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;
    private final IncidentEventRepository incidentEventRepository;

    public IncidentService(IncidentRepository incidentRepository, IncidentMapper incidentMapper, IncidentEventRepository incidentEventRepository) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
        this.incidentEventRepository = incidentEventRepository;
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

        IncidentEvent event = new IncidentEvent(incident, IncidentEventType.ACKNOWLEDGED, incident.getDescription());

        incidentEventRepository.save(event);

        var savedIncident = incidentRepository.save(incident);
        return incidentMapper.toIncidentResponse(savedIncident);
    }


    public IncidentResponse setResolved(UUID uuid) {

        var incident = incidentRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Incident not found"));

        if (incident.getStatus() != IncidentStatus.RESOLVED) {

            incident.setStatus(IncidentStatus.RESOLVED);
            incident.setResolvedAt(LocalDateTime.now());
        }

        IncidentEvent event = new IncidentEvent(incident, IncidentEventType.RESOLVED, incident.getDescription());

        incidentEventRepository.save(event);

        var savedIncident = incidentRepository.save(incident);

        return incidentMapper.toIncidentResponse(savedIncident);
    }
}
