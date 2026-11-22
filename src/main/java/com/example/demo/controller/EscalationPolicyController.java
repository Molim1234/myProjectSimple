package com.example.demo.controller;


import com.example.demo.dto.AddEscalationLevelRequest;
import com.example.demo.service.EscalationPolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/escalation-policies")
public class EscalationPolicyController {

    private final EscalationPolicyService escalationPolicyService;

    @PostMapping("/{teamId}/levels")
    public void addEscalationLevel(@PathVariable UUID teamId,
                                   @RequestBody @Valid AddEscalationLevelRequest addEscalationLevelRequest
                                   ){
        escalationPolicyService.addEscalationLevel(teamId, addEscalationLevelRequest);
    }




}
