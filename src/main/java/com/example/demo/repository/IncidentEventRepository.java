package com.example.demo.repository;

import com.example.demo.entity.IncidentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IncidentEventRepository extends JpaRepository<IncidentEvent, UUID> {

    List<IncidentEvent> findAllByIncident_IdOrderByCreatedAtAsc(UUID incidentId);

}
