package dev.project.project_manager.Controllers;

import dev.project.project_manager.Repository.TeamRepository;
import dev.project.project_manager.Services.teams.TeamService;
import dev.project.project_manager.models.Team;
import dev.project.project_manager.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project-teams")
public class TeamController {

    private final TeamService teamService;

    private final TeamRepository teamRepository;

    @PostMapping("/new-team")
    public ResponseEntity<String> createTeam(@RequestBody Team team){
        teamService.createTeam(team);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/update-team")
    public ResponseEntity<String> updateTeam(@RequestBody Map<String,String> body){
        String name = body.get("name");
        String leadid = body.get("leader");
        String memberid = body.get("member");

        var found = teamRepository.findByName(name);
        if(found.isEmpty()){
            return new ResponseEntity<>("Team not found!", HttpStatus.NOT_FOUND);
        }

        Team team = found.get();
        if(! (team.getLeaderid()== Long.parseLong(leadid))){
            return new ResponseEntity<>("Leader no does not match!", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Set<Long> crtmember=team.getMembers();
        crtmember.add(Long.parseLong(memberid));
        team.setMembers(crtmember);
        teamRepository.save(team);

        return new ResponseEntity<>("Team updated successful!", HttpStatus.OK);
    }
}
