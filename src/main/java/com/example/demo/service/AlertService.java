package com.example.demo.service;

import com.example.demo.dto.CreateAlert;
import com.example.demo.entity.Alert;
import com.example.demo.entity.Incident;
import com.example.demo.mapper.AlertMapper;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.IncidentRepository;
import com.example.demo.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;
    private final ServiceRepository serviceRepository;
    private final IncidentRepository incidentRepository;

    public AlertService(AlertRepository alertRepository, AlertMapper alertMapper, ServiceRepository serviceRepository, IncidentRepository incidentRepository) {
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
        this.serviceRepository = serviceRepository;
        this.incidentRepository = incidentRepository;
    }

    public Alert addAlert(CreateAlert createAlert) {
        var alert = alertMapper.toAlert(createAlert);
        var service = serviceRepository.findById(createAlert.getServiceId()).orElseThrow(()->new NoSuchElementException("Service not found"));
        alert.setService(service);

        var optionalIncident = incidentRepository.findIncidentByServiceAndError(createAlert.getServiceId(), createAlert.getErrorCode());
Incident incident;
        if(optionalIncident.isEmpty()){
            incident = new Incident();
            incident.setErrorCode(alert.getErrorCode());
            incident.setService(service);
            incident.setDescription(alert.getMessage());
            incident.addAlert(alert);
        }else{
            incident = optionalIncident.get();
            incident.addAlert(alert);
        }
        incidentRepository.save(incident);
        return alertRepository.save(alert);
    }
}
