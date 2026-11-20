package com.example.demo.controller;

import com.example.demo.dto.AssignIncident;
import com.example.demo.dto.IncidentEventResponse;
import com.example.demo.dto.IncidentResponse;
import com.example.demo.service.IncidentEventService;
import com.example.demo.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;
    private final IncidentEventService incidentEventService;

    public IncidentController(IncidentService incidentService, IncidentEventService incidentEventService) {
        this.incidentService = incidentService;
        this.incidentEventService = incidentEventService;
    }

    @GetMapping
    public List<IncidentResponse> getAllIncidents(){
        return incidentService.getAllIncidents();
    }

    @GetMapping("/{uuid}")
    public IncidentResponse getIncident(@PathVariable UUID uuid){
        return incidentService.getIncident(uuid);
    }

    @GetMapping("/{uuid}/events")
    public List<IncidentEventResponse> getIncidentEvents(@PathVariable UUID uuid){
       return incidentEventService.getIncidentEvents(uuid);
    }

    @PatchMapping("/{uuid}/acknowledge")
    public IncidentResponse setAcknowledge(@PathVariable UUID uuid){
        return incidentService.setAcknowledge(uuid);
    }

    @PatchMapping("/{uuid}/resolve")
    public IncidentResponse setResolved(@PathVariable UUID uuid){
        return incidentService.setResolved(uuid);
    }

    @PatchMapping("/{uuid}/assign")
    public IncidentResponse assignIncident(@RequestBody @Valid AssignIncident assignIncident, @PathVariable UUID uuid){
       return incidentService.assignIncident(assignIncident, uuid);
    }

    @GetMapping("/assigned/{userId}")
    public List<IncidentResponse> getIncidentsByUser(@PathVariable UUID userId){
        return incidentService.getIncidentsByUser(userId);
    }



}
