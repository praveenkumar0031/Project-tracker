package dev.project.project_manager.Services.teams;

import dev.project.project_manager.models.Team;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeamService {
    Optional<Team> getTeamById(Long id);
    Optional<Team> getTeamByLeaderId(Long leaderId);
    Set<Long> getAllMembersId(Long id);

    List<Team> getAllTeams();
    Team createTeam(Team team);
    Team updateTeam(Long id,Team team);
    void deleteTeam(Long teamid);

}
