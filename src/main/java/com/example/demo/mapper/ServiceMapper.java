package com.example.demo.mapper;

import com.example.demo.dto.CreateService;
import com.example.demo.entity.Services;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public Services toServices(CreateService createService){
        Services s = new Services();
        s.setName(createService.getName());
        s.setDescription(createService.getDescription());
        s.setActive(createService.isActive());
        s.setBaseUrl(createService.getBaseUrl());
        return s;
    }

    public void updateServices(Services service, CreateService createService) {
        service.setName(createService.getName());
        service.setDescription(createService.getDescription());
        service.setBaseUrl(createService.getBaseUrl());
        service.setActive(createService.isActive());
    }



}
