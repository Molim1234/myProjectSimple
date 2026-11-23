package com.example.demo.service;

import com.example.demo.dto.AlertResponse;
import com.example.demo.dto.CreateAlert;
import com.example.demo.entity.*;
import com.example.demo.mapper.AlertMapper;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.IncidentEventRepository;
import com.example.demo.repository.IncidentRepository;
import com.example.demo.repository.ServiceRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;
    private final ServiceRepository serviceRepository;
    private final IncidentRepository incidentRepository;
    private final IncidentEventRepository incidentEventRepository;

    public AlertService(AlertRepository alertRepository, AlertMapper alertMapper, ServiceRepository serviceRepository, IncidentRepository incidentRepository, IncidentEventRepository incidentEventRepository) {
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
        this.serviceRepository = serviceRepository;
        this.incidentRepository = incidentRepository;
        this.incidentEventRepository = incidentEventRepository;
    }


    @Transactional
    public AlertResponse addAlert(CreateAlert createAlert) {

        var alert = alertMapper.toAlert(createAlert);

        var service = serviceRepository.findById(createAlert.getServiceId())
                .orElseThrow(() -> new NoSuchElementException("Service not found"));

        alert.setService(service);

        var optionalIncident = incidentRepository.findIncidentByServiceAndError(
                createAlert.getServiceId(),
                createAlert.getErrorCode()
        );

        Incident incident;
        if (optionalIncident.isEmpty()) {

            incident = new Incident();
            incident.setErrorCode(alert.getErrorCode());
            incident.setService(service);
            incident.setDescription(alert.getMessage());

            var levels = service.getTeam().getEscalationPolicy().getLevels();
            User firstLevelUser = null;
            for (EscalationLevel level : levels) {
                if (level.getLevel() == 1) {
                    firstLevelUser = level.getUser();
                    break;
                }
            }

            if (firstLevelUser == null) {
                throw new NoSuchElementException("Didnt find the user with level 1");
            }

            incident.setAssignedUser(firstLevelUser);
            incident.setCurrentEscalationLevel(1);

            boolean hasNextLevel = levels.stream()
                    .anyMatch(level -> level.getLevel() == 2);
            if (hasNextLevel) {
                EscalationLevel firstLevel = levels.stream()
                        .filter(level -> level.getLevel() == 1)
                        .findFirst()
                        .orElseThrow();
                incident.setNextEscalationAt(
                        LocalDateTime.now().plusMinutes(firstLevel.getTimeoutMinutes())
                );
            }

            incidentRepository.save(incident);

            // Record creation before assignment so the event history is chronological.
            IncidentEvent createdEvent = new IncidentEvent(
                    incident,
                    IncidentEventType.CREATED,
                    alert.getMessage()
            );
            incident.addEvent(createdEvent);
            incidentEventRepository.save(createdEvent);

            IncidentEvent assignedEvent = new IncidentEvent(
                    incident,
                    IncidentEventType.ASSIGNED,
                    "Incident assigned to " + firstLevelUser.getFirstName() + " " + firstLevelUser.getLastName()
            );
            incident.addEvent(assignedEvent);
            incidentEventRepository.save(assignedEvent);

        } else {
            incident = optionalIncident.get();

            IncidentEvent alertAddedEvent = new IncidentEvent(
                    incident,
                    IncidentEventType.ALERT_ADDED,
                    alert.getMessage()
            );
            incident.addEvent(alertAddedEvent);
            incidentEventRepository.save(alertAddedEvent);
        }

        incident.addAlert(alert);
        var savedAlert = alertRepository.save(alert);

        return alertMapper.toAlertResponse(savedAlert);
    }

}
