package com.example.demo.controller;

import com.example.demo.dto.CreateService;
import com.example.demo.entity.Services;
import com.example.demo.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

