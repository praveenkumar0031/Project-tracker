package dev.project.project_manager.Services.teams;

import dev.project.project_manager.Repository.TeamRepository;
import dev.project.project_manager.models.Team;
import dev.project.project_manager.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamServices implements TeamService {
    private final TeamRepository teamRepository;
    public TeamServices(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    @Override
    public Optional<Team> getTeamById(Long id){
        return teamRepository.findById(id);
    }
    @Override
    public Optional<Team>getTeamByLeaderId(Long leaderId){
        return teamRepository.findByLeaderid(leaderId);
    }


    @Override
    public Set<Long> getAllMembersId(Long id){
        return teamRepository.findAllMembersByTeamId(id);
    }
    @Override
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }
    @Override
    public Team createTeam(Team team){
        return teamRepository.save(team);
    }
    @Override
    public Team updateTeam(Long id, Team team) {
        return teamRepository.findById(id)
                .map(existing -> {
                    existing.setName(team.getName());
                    existing.setMembers(team.getMembers());
                    return teamRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public void deleteTeam(Long teamid){
         teamRepository.deleteById(teamid);
    }
}
