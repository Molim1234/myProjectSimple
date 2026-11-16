package com.example.demo.service;


import com.example.demo.dto.CreateTeam;
import com.example.demo.dto.TeamResponse;
import com.example.demo.exception.ConflictException;
import com.example.demo.mapper.TeamMapper;
import com.example.demo.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public TeamResponse createTeam(CreateTeam createTeam) {
        var team = teamMapper.toTeam(createTeam);
        var savedTeam = teamRepository.save(team);
        return teamMapper.toTeamResponse(savedTeam);
    }


    public List<TeamResponse> getAllTeams() {
        var list = teamRepository.findAll();
        return teamMapper.toTeamResponseList(list);
    }

    public TeamResponse getTeam(UUID uuid) {
        var team = teamRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Team not found"));
        return teamMapper.toTeamResponse(team);
    }

    public void deleteTeam(UUID uuid) {
        var team = teamRepository.findById(uuid).orElseThrow(()-> new NoSuchElementException("Team not found"));
        if(!team.getUsers().isEmpty()){
            throw new ConflictException("Can't delete team with users");
        }
        teamRepository.delete(team);
    }
}
