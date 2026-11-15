package com.example.demo.service;

import com.example.demo.dto.CreateService;
import com.example.demo.entity.Services;
import com.example.demo.mapper.ServiceMapper;
import com.example.demo.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    public Services createService(CreateService createService) {
        var service = serviceMapper.toServices(createService);
        return serviceRepository.save(service);
    }
}
