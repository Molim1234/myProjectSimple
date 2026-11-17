package com.example.demo.service;

import com.example.demo.dto.AssignIncident;
import com.example.demo.dto.IncidentEventResponse;
import com.example.demo.dto.IncidentResponse;
import com.example.demo.entity.IncidentEvent;
import com.example.demo.entity.IncidentEventType;
import com.example.demo.entity.IncidentStatus;
import com.example.demo.exception.ConflictException;
import com.example.demo.mapper.IncidentMapper;
import com.example.demo.repository.IncidentEventRepository;
import com.example.demo.repository.IncidentRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    private final UserRepository userRepository;

    public IncidentService(IncidentRepository incidentRepository, IncidentMapper incidentMapper, IncidentEventRepository incidentEventRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
        this.incidentEventRepository = incidentEventRepository;
        this.userRepository = userRepository;
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

        var incident = incidentRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Incident not found"));

        if (incident.getStatus() == IncidentStatus.RESOLVED) {
            throw new ConflictException("Resolved incident cannot be acknowledged");
        }

        if (incident.getStatus() == IncidentStatus.ACKNOWLEDGED) {
            return incidentMapper.toIncidentResponse(incident);
        }

        incident.setStatus(IncidentStatus.ACKNOWLEDGED);


        IncidentEvent event = new IncidentEvent(
                incident,
                IncidentEventType.ACKNOWLEDGED,
                "Incident acknowledged"
        );

        incident.addEvent(event);
        incidentEventRepository.save(event);

        var savedIncident = incidentRepository.save(incident);

        return incidentMapper.toIncidentResponse(savedIncident);
    }


    public IncidentResponse setResolved(UUID uuid) {

        var incident = incidentRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Incident not found"));

        if (incident.getStatus() == IncidentStatus.RESOLVED) {
            return incidentMapper.toIncidentResponse(incident);
        }

        incident.setStatus(IncidentStatus.RESOLVED);
        incident.setResolvedAt(LocalDateTime.now());

        IncidentEvent event = new IncidentEvent(
                incident,
                IncidentEventType.RESOLVED,
                "Incident resolved"
        );

        incident.addEvent(event);
        incidentEventRepository.save(event);

        var savedIncident = incidentRepository.save(incident);

        return incidentMapper.toIncidentResponse(savedIncident);
    }

@Transactional
    public void assignIncident(@Valid AssignIncident assignIncident, UUID uuid) {
        var incident = incidentRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Incident not found"));
        var user = userRepository.findById(assignIncident.getUserId()).orElseThrow(()-> new NoSuchElementException("User not found"));
        if(user.getTeam().getId() != incident.getService().getTeam().getId()){
            throw new ConflictException("Team conflict");
        }
        incident.setAssignedUser(user);
        incidentRepository.save(incident);
    }
}
