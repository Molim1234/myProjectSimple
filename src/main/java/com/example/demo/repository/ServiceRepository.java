package com.example.demo.repository;

import com.example.demo.entity.Services;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Services, UUID> {

    boolean existsByTeam_Id(UUID teamId);

    @Query("select count(s) from Services s")
    long countServices();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Services s where s.id = :id")
    Optional<Services> findByIdForUpdate(@Param("id") UUID id);
}
