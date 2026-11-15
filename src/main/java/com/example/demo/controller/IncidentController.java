package com.example.demo.controller;

import com.example.demo.dto.IncidentResponse;
import com.example.demo.service.IncidentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    public List<IncidentResponse> getAllIncidents(){
        return incidentService.getAllIncidents();
    }

    @GetMapping("/{uuid}")
    public IncidentResponse getIncident(@PathVariable UUID uuid){
        return incidentService.getIncident(uuid);
    }

    @PatchMapping("/{uuid}/acknowledge")
    public IncidentResponse setAcknowledge(@PathVariable UUID uuid){
        return incidentService.setAcknowledge(uuid);
    }

    @PatchMapping("/{uuid}/resolved")
    public IncidentResponse setResolved(@PathVariable UUID uuid){
        return incidentService.setResolved(uuid);
    }

}
