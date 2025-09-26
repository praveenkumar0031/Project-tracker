package dev.project.project_manager.Services.projects;

import dev.project.project_manager.Repository.ProjectRepository;
import dev.project.project_manager.Repository.TaskRepository;
import dev.project.project_manager.Repository.TeamRepository;
import dev.project.project_manager.Repository.UserRepository;
import dev.project.project_manager.models.Project;
import dev.project.project_manager.models.Task;
import dev.project.project_manager.models.Team;
import dev.project.project_manager.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServices implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ProjectServices(ProjectRepository projectRepository,
                           TeamRepository teamRepository,
                           UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;

    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long projectId, Project projectDetails) {
        return projectRepository.findById(projectId)
                .map(existing -> {
                    existing.setProject_title(projectDetails.getProject_title());
                    existing.setOwner(projectDetails.getOwner());
                    return projectRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
    }

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found with id: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }

    // 🔹 New Features

    @Override
    public Project assignOwner(Long projectId, User owner) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User dbOwner = userRepository.findById(owner.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        project.setOwner(dbOwner);
        return projectRepository.save(project);
    }



    @Override
    public Project removeTeamFromProject(Long projectId, Team team) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Team dbTeam = teamRepository.findById(team.getId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        project.getTeams().remove(dbTeam);
        return projectRepository.save(project);
    }

    @Override
    public Project addTaskToProject(Long projectId, Long taskId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setProject(project); // link task to project
        project.getTasks().add(task);

        return projectRepository.save(project);
    }
    @Override
    public Project assignTeamToProject(Long projectId, Long teamId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        project.getTeams().add(team);
        return projectRepository.save(project);
    }

    @Override
    public Set<Team> getProjectTeams(Long projectId) {
        return projectRepository.findById(projectId)
                .map(Project::getTeams)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public Set<Task> getProjectTasks(Long projectId) {
        return projectRepository.findById(projectId)
                .map(Project::getTasks)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

}