package com.example.demo.service;

import com.example.demo.dto.CreateService;
import com.example.demo.entity.Services;
import com.example.demo.mapper.ServiceMapper;
import com.example.demo.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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

    public Services getService(UUID uuid) {
        return serviceRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Service not found")
        );
    }

    @Transactional
    public Services updateService(UUID uuid, CreateService createService) {
        var service = serviceRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Service not found"));

        serviceMapper.updateServices(service, createService);
        return serviceRepository.save(service);
    }

    public Services deleteService(UUID uuid) {
        var service = serviceRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Service not found"));
        serviceRepository.delete(service);
        return service;
    }
}
