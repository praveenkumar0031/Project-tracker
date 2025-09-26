package dev.project.project_manager.Services.teams;
import dev.project.project_manager.models.Team;
import dev.project.project_manager.models.User;
import dev.project.project_manager.models.Project;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeamService {
    Team createTeam(Team team);
    Team updateTeam(Long teamId, Team teamDetails);
    Optional<Team> getTeamById(Long teamId);
    List<Team> getAllTeams();
    void deleteTeam(Long teamId);

    // Leader handling
    Team assignLeader(Long teamId, User leader);

    // Member handling
    Team addMember(Long teamId, User member);
    Team removeMember(Long teamId, User member);
    Set<User> getTeamMembers(Long teamId);

    // Project handling
    Set<Project> getTeamProjects(Long teamId);
}