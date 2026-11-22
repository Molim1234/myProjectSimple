package com.example.demo.repository;

import com.example.demo.entity.EscalationPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EscalationPolicyRepository extends JpaRepository<EscalationPolicy, UUID> {

    Optional<EscalationPolicy> findByTeam_Id(UUID teamId);

}
