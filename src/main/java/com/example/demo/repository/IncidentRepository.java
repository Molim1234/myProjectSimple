package com.example.demo.repository;

import com.example.demo.entity.Incident;
import com.example.demo.entity.IncidentStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select i from Incident i
            where i.service.id = :serviceId
            and i.errorCode = :errorCode
            and i.status in :statuses
            """)
    Optional<Incident> findActiveIncidentByServiceAndErrorForUpdate(
            @Param("serviceId") UUID serviceId,
            @Param("errorCode") String errorCode,
            @Param("statuses") List<IncidentStatus> statuses
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Incident i where i.id = :id")
    Optional<Incident> findByIdForUpdate(@Param("id") UUID id);

    List<Incident> findIncidentsByAssignedUser_Id(UUID assignedUserId);

    boolean existsByIdAndAssignedUser_EmailIgnoreCase(UUID incidentId, String email);

    @Query("select i from Incident i where i.assignedUser.id = :userId and i.status in :statuses")
    List<Incident> findActiveIncidentsByUser_Id(
            @Param("userId") UUID userId,
            @Param("statuses") List<IncidentStatus> statuses
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select i from Incident i
            where i.status = :status
            and i.nextEscalationAt is not null
            and i.nextEscalationAt <= :now
            """)
    List<Incident> findOverdueIncidentsForEscalationForUpdate(
            @Param("status") IncidentStatus status,
            @Param("now") LocalDateTime now
    );

    List<Incident> findIncidentsByStatus(IncidentStatus status);

    long countAllByStatus(IncidentStatus status);

    @Query(value = "select * from incidents i order by created_at desc limit 10", nativeQuery = true)
    List<Incident> returnLast10Incidents();

    @Query("select count(*) from Incident i where i.status in :statuses and i.service.id = :serviceId")
    long numberOfOpenAndAcknowledgedByServiceId(
            @Param("serviceId") UUID serviceId,
            @Param("statuses") List<IncidentStatus> statuses
    );
}
