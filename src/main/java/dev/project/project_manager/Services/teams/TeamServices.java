package dev.project.project_manager.Services.teams;



import dev.project.project_manager.Repository.TeamRepository;
import dev.project.project_manager.Repository.UserRepository;
import dev.project.project_manager.models.Project;
import dev.project.project_manager.models.Team;
import dev.project.project_manager.models.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamServices implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamServices(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Long teamId, Team teamDetails) {
        return teamRepository.findById(teamId)
                .map(existing -> {
                    existing.setTeamname(teamDetails.getTeamname());
                    existing.setLeader(teamDetails.getLeader());
                    return teamRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + teamId));
    }

    @Override
    public Optional<Team> getTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public void deleteTeam(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Team not found with id: " + teamId);
        }
        teamRepository.deleteById(teamId);
    }

    // 🔹 Leader handling
    @Override
    public Team assignLeader(Long teamId, User leader) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        User dbLeader = userRepository.findById(leader.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        team.setLeader(dbLeader);
        return teamRepository.save(team);
    }

    // 🔹 Member handling
    @Override
    public Team addMember(Long teamId, User member) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        User dbUser = userRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (dbUser.getTeam() != null && !dbUser.getTeam().getId().equals(teamId)) {
            throw new RuntimeException("User already belongs to another team");
        }

        dbUser.setTeam(team);
        userRepository.save(dbUser);

        team.getMembers().add(dbUser);
        return teamRepository.save(team);
    }

    @Override
    public Team removeMember(Long teamId, User member) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        User dbUser = userRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (team.getMembers().contains(dbUser)) {
            team.getMembers().remove(dbUser);
            dbUser.setTeam(null); // remove link
            userRepository.save(dbUser);
        }

        return teamRepository.save(team);
    }

    @Override
    public Set<User> getTeamMembers(Long teamId) {
        return teamRepository.findById(teamId)
                .map(Team::getMembers)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }


    @Override
    public Set<Project> getTeamProjects(Long teamId) {
        return teamRepository.findById(teamId)
                .map(Team::getProjects)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }
}
