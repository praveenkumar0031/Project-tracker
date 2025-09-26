package dev.project.project_manager.Controllers;

import dev.project.project_manager.Services.projects.ProjectService;
import dev.project.project_manager.models.Project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project-manager/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // 🔹 Create a new Project
    @PostMapping("/new1")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    // 🔹 Get all Projects
    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // 🔹 Get Project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Update Project (title, owner, teams, tasks)
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDetails));
    }

    // 🔹 Delete Project
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted successfully!");
    }

    // 🔹 Assign Team to Project
    @PostMapping("/{projectId}/assign-team/{teamId}")
    public ResponseEntity<Project> assignTeamToProject(@PathVariable Long projectId, @PathVariable Long teamId) {
        return ResponseEntity.ok(projectService.assignTeamToProject(projectId, teamId));
    }

    // 🔹 Add Task to Project
    @PostMapping("/{projectId}/add-task/{taskId}")
    public ResponseEntity<Project> addTaskToProject(@PathVariable Long projectId, @PathVariable Long taskId) {
        return ResponseEntity.ok(projectService.addTaskToProject(projectId, taskId));
    }
}

