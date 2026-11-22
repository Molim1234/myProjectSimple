package com.example.demo.service;

import com.example.demo.dto.AddEscalationLevelRequest;
import com.example.demo.dto.EscalationLevelResponse;
import com.example.demo.entity.EscalationLevel;
import com.example.demo.mapper.EscalationLevelMapper;
import com.example.demo.repository.EscalationPolicyRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EscalationPolicyService {

    private final EscalationPolicyRepository escalationPolicyRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final EscalationLevelMapper escalationLevelMapper;



    @Transactional
    public void addEscalationLevel(UUID teamId, AddEscalationLevelRequest addEscalationLevelRequest) {

        var policy = escalationPolicyRepository.findByTeam_Id(teamId)
                .orElseThrow(() -> new NoSuchElementException("Escalation policy not found"));

        var team = policy.getTeam();
        var user = userRepository.findById(addEscalationLevelRequest.getUserId()).orElseThrow(()-> new NoSuchElementException("User not found"));

        if (!user.getTeam().getId().equals(team.getId())) {
            throw new IllegalArgumentException("User does not belong to this team");
        }


        Integer nextLevel = policy.getLevels().stream()
                .map(EscalationLevel::getLevel)
                .max(Integer::compareTo)
                .orElse(0) + 1;

        EscalationLevel level = new EscalationLevel(nextLevel, addEscalationLevelRequest.getTimeoutMinutes(), user);

        policy.addLevel(level);
        escalationPolicyRepository.save(policy);
    }


    public List<EscalationLevelResponse> getAllLevelsByteam(UUID teamId) {
        var team = teamRepository.findById(teamId).orElseThrow(()-> new NoSuchElementException("Team not found"));
        var levels = team.getEscalationPolicy().getLevels();
        return escalationLevelMapper.toEscalationLevelResponseList(levels);
    }
}
