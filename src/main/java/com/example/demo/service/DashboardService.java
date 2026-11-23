package com.example.demo.service;

import com.example.demo.dto.DashboardResponse;
import com.example.demo.entity.IncidentStatus;
import com.example.demo.mapper.IncidentMapper;
import com.example.demo.repository.IncidentRepository;
import com.example.demo.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IncidentRepository incidentRepository;
    private final ServiceRepository serviceRepository;
    private final IncidentMapper incidentMapper;

    @Transactional(readOnly = true)
    public DashboardResponse dashboardResponse() {

        long open = incidentRepository.countAllByStatus(IncidentStatus.OPEN);
        long acknowledged = incidentRepository.countAllByStatus(IncidentStatus.ACKNOWLEDGED);
        long resolved = incidentRepository.countAllByStatus(IncidentStatus.RESOLVED);

        long services = serviceRepository.countServices();

        var incidents = incidentRepository.returnLast10Incidents();

        var incidentsResponse = incidentMapper.toIncidentResponseList(incidents);

        return new DashboardResponse(open, acknowledged, resolved, services, incidentsResponse);
    }
}
