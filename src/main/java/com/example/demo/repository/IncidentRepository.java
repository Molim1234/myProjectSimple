package com.example.demo.repository;

import com.example.demo.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {

   @Query(value = "select * from incidents where service_id=:uuid and error_code =:error" +
           " and status in ('OPEN','ACKNOWLEDGED')", nativeQuery = true)
    Optional<Incident> findIncidentByServiceAndError(UUID uuid, String error);

}
