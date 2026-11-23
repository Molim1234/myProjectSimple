package com.example.demo.security;

import com.example.demo.repository.IncidentRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("incidentAccess")
public class IncidentAccess {
    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    public IncidentAccess(IncidentRepository incidentRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
    }

    public boolean canAccessIncident(Authentication authentication, UUID incidentId) {
        if (isAdmin(authentication)) {
            return true;
        }
        return incidentRepository.existsByIdAndAssignedUser_EmailIgnoreCase(
                incidentId, authentication.getName());
    }

    public boolean canAccessUserIncidents(Authentication authentication, UUID userId) {
        if (isAdmin(authentication)) {
            return true;
        }
        return userRepository.findByEmailIgnoreCase(authentication.getName())
                .map(user -> user.getId().equals(userId))
                .orElse(false);
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }
}
