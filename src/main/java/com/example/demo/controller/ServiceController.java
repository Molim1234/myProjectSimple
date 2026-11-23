package com.example.demo.controller;

import com.example.demo.dto.CreateService;
import com.example.demo.dto.ServiceHealthResponse;
import com.example.demo.dto.UpdateService;
import com.example.demo.entity.Services;
import com.example.demo.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }


    @GetMapping
    public List<Services> getAllServices(){
        return serviceService.getAllServices();
    }

    @PostMapping
    public Services createService(@RequestBody @Valid CreateService createService){
        return serviceService.createService(createService);
    }

    @GetMapping("/{uuid}")
    public Services getService(@PathVariable UUID uuid){
        return serviceService.getService(uuid);
    }

    @PutMapping("/{uuid}")
    public Services updateService(@PathVariable UUID uuid, @RequestBody @Valid UpdateService updateService){
        return serviceService.updateService(uuid, updateService);
    }

    @DeleteMapping("/{uuid}")
    public Services deleteService(@PathVariable UUID uuid){
        return serviceService.deleteService(uuid);
    }

    @GetMapping("/health")
    public List<ServiceHealthResponse> getServicesHealth(){
        return serviceService.getServicesHealth();
    }
}

