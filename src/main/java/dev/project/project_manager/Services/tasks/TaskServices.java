package dev.project.project_manager.Services.tasks;


import dev.project.project_manager.Repository.ProjectRepository;
import dev.project.project_manager.Repository.TaskRepository;
import dev.project.project_manager.Repository.UserRepository;
import dev.project.project_manager.models.Task;
import dev.project.project_manager.models.TaskStatus;
import dev.project.project_manager.models.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServices implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskServices(TaskRepository taskRepository,
                           ProjectRepository projectRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(Task task) {
        // ensure project exists
        if (task.getProject() == null ||
                !projectRepository.existsById(task.getProject().getId())) {
            throw new RuntimeException("Invalid project for task");
        }
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, Task taskDetails) {
        return taskRepository.findById(taskId)
                .map(existing -> {
                    existing.setTitle(taskDetails.getTitle());
                    existing.setDescription(taskDetails.getDescription());
                    existing.setStatus(taskDetails.getStatus());
                    existing.setDueDate(taskDetails.getDueDate());
                    return taskRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }

    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }

    // 🔹 Assign task to a user
    @Override
    public Task assignUser(Long taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User dbUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(dbUser);
        return taskRepository.save(task);
    }

    // 🔹 Update task status
    @Override
    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);
        return taskRepository.save(task);
    }

    // 🔹 Get tasks by project
    @Override
    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    // 🔹 Get tasks by user
    @Override
    public List<Task> getTasksByUser(Long userId) {
        return taskRepository.findByUser_Id(userId);
    }
}

