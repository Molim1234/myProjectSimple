package com.example.demo.repository;

import com.example.demo.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Services, UUID> {

    boolean existsByTeam_Id(UUID teamId);

    @Query("select count(s) from Services s")
    long countServices();
}
