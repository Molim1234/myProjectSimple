package com.example.demo.service;

import com.example.demo.dto.CreateService;
import com.example.demo.dto.UpdateService;
import com.example.demo.entity.Services;
import com.example.demo.mapper.ServiceMapper;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final TeamRepository teamRepository;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper, TeamRepository teamRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.teamRepository = teamRepository;
    }

    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    public Services createService(CreateService createService) {
        var team = teamRepository.findById(createService.getTeamId()).orElseThrow(()-> new NoSuchElementException("Team not found"));
        var service = serviceMapper.toServices(createService);
        service.setTeam(team);
        return serviceRepository.save(service);
    }

    public Services getService(UUID uuid) {
        return serviceRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Service not found")
        );
    }

    @Transactional
    public Services updateService(UUID uuid, UpdateService updateService) {
        var service = serviceRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Service not found"));

        serviceMapper.updateServices(service, updateService);
        return serviceRepository.save(service);
    }

    public Services deleteService(UUID uuid) {
        var service = serviceRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Service not found"));
        serviceRepository.delete(service);
        return service;
    }
}
