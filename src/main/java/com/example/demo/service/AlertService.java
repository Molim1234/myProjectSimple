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
        IncidentEvent event = new IncidentEvent();
        if (optionalIncident.isEmpty()) {
            incident = new Incident();
            incident.setErrorCode(alert.getErrorCode());
            incident.setService(service);
            incident.setDescription(alert.getMessage());

            event.setEventType(IncidentEventType.CREATED);

            var lista = service.getTeam().getEscalationPolicy().getLevels();
            User u=null;
            for(EscalationLevel l:lista){
                if(l.getLevel()==1){
                    u=l.getUser();
                    break;
                }
            }
            if(u==null){
                throw new NoSuchElementException("Didnt find the user with level 1");
            }
            incident.setAssignedUser(u);
            incidentRepository.save(incident);

        } else {
            incident = optionalIncident.get();

            event.setEventType(IncidentEventType.ALERT_ADDED);
        }
        incident.addEvent(event);
        event.setDescription(alert.getMessage());

        incidentEventRepository.save(event);

        incident.addAlert(alert);
        var savedAlert = alertRepository.save(alert);

        return alertMapper.toAlertResponse(savedAlert);
    }

}
