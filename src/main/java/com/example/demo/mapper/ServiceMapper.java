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



}
