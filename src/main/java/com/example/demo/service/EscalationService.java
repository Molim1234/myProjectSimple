package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.IncidentEventRepository;
import com.example.demo.repository.IncidentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class EscalationService {

    private final IncidentRepository incidentRepository;
    private final IncidentEventRepository incidentEventRepository;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void checkIncidentsForEscalation() {
        LocalDateTime now = LocalDateTime.now();

        var incidents = incidentRepository.findOverdueIncidentsForEscalation(
                IncidentStatus.OPEN,
                now
        );

        for (Incident incident : incidents) {
            var levels = incident.getService()
                    .getTeam()
                    .getEscalationPolicy()
                    .getLevels();

            int currentLevel = incident.getCurrentEscalationLevel() == null
                    ? 0
                    : incident.getCurrentEscalationLevel();

            var nextLevel = levels.stream()
                    .filter(level -> level.getLevel() > currentLevel)
                    .min(Comparator.comparingInt(EscalationLevel::getLevel))
                    .orElse(null);

            if (nextLevel == null) {

                incident.setNextEscalationAt(null);
                incidentRepository.save(incident);
                continue;
            }

            boolean hasFollowingLevel = levels.stream()
                    .anyMatch(level -> level.getLevel() > nextLevel.getLevel());

            if (hasFollowingLevel) {
                incident.setNextEscalationAt(
                        now.plusMinutes(nextLevel.getTimeoutMinutes())
                );
            } else {

                incident.setNextEscalationAt(null);
            }

            incident.setCurrentEscalationLevel(nextLevel.getLevel());
            incident.setAssignedUser(nextLevel.getUser());

            IncidentEvent event = new IncidentEvent(
                    incident,
                    IncidentEventType.ASSIGNED,
                    "Incident assigned to " + nextLevel.getUser().getFirstName()
                            + " " + nextLevel.getUser().getLastName()
            );

            incident.addEvent(event);
            incidentEventRepository.save(event);
            incidentRepository.save(incident);
        }
    }
}
