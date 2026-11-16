package com.example.demo.controller;

import com.example.demo.dto.CreateTeam;
import com.example.demo.dto.TeamResponse;
import com.example.demo.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public TeamResponse createTeam(@RequestBody @Valid CreateTeam createTeam){
        return teamService.createTeam(createTeam);
    }

    @GetMapping
    public List<TeamResponse> getAllTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/{uuid}")
    public TeamResponse getTeam(@PathVariable UUID uuid){
        return teamService.getTeam(uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteTeam(@PathVariable UUID uuid){
        teamService.deleteTeam(uuid);
    }

}
