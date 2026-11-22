package com.example.demo.service;

import com.example.demo.dto.AddEscalationLevelRequest;
import com.example.demo.entity.EscalationLevel;
import com.example.demo.repository.EscalationPolicyRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EscalationPolicyService {

    private final EscalationPolicyRepository escalationPolicyRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;


    @Transactional
    public void addEscalationLevel(UUID teamId, AddEscalationLevelRequest addEscalationLevelRequest) {


        var team = teamRepository.findById(teamId).orElseThrow(()->new NoSuchElementException("Team not found"));
        var user = userRepository.findById(addEscalationLevelRequest.getUserId()).orElseThrow(()-> new NoSuchElementException("User not found"));

        if (!user.getTeam().getId().equals(team.getId())) {
            throw new IllegalArgumentException("User does not belong to this team");
        }

        var policy = escalationPolicyRepository.findByTeam_Id(teamId)
                .orElseThrow(() -> new NoSuchElementException("Escalation policy not found"));

        Integer nextLevel = policy.getLevels().stream()
                .map(EscalationLevel::getLevel)
                .max(Integer::compareTo)
                .orElse(0) + 1;

        EscalationLevel level = new EscalationLevel(nextLevel, addEscalationLevelRequest.getTimeoutMinutes(), user);

        policy.addLevel(level);
        escalationPolicyRepository.save(policy);
    }



}
