package dev.project.project_manager.Services.projects;

import dev.project.project_manager.models.Project;
import dev.project.project_manager.models.Task;
import dev.project.project_manager.models.Team;
import dev.project.project_manager.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectService {
    Project createProject(Project project);
    Project updateProject(Long projectId, Project projectDetails);
    Optional<Project> getProjectById(Long projectId);
    List<Project> getAllProjects();
    void deleteProject(Long projectId);


    Project assignOwner(Long projectId, User owner);
    Project addTaskToProject(Long projectId, Long taskId);
    Project assignTeamToProject(Long projectId, Long teamId);
    Project removeTeamFromProject(Long projectId, Team team);
    Set<Team> getProjectTeams(Long projectId);
    Set<Task> getProjectTasks(Long projectId);


}
