
package com.example.demo.mapper;

import com.example.demo.dto.CreateTeam;
import com.example.demo.dto.TeamResponse;
import com.example.demo.entity.Team;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamMapper {

    public Team toTeam(CreateTeam createTeam) {
        return new Team(createTeam.getName());
    }

    public TeamResponse toTeamResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName()
        );
    }

    public List<TeamResponse> toTeamResponseList(List<Team> teams) {
        return teams.stream()
                .map(this::toTeamResponse)
                .toList();
    }
}
