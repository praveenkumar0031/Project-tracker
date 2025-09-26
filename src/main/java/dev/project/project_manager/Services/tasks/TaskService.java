package dev.project.project_manager.Services.tasks;


import dev.project.project_manager.models.Task;
import dev.project.project_manager.models.TaskStatus;
import dev.project.project_manager.models.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    Task updateTask(Long taskId, Task taskDetails);
    Optional<Task> getTaskById(Long taskId);
    List<Task> getAllTasks();
    void deleteTask(Long taskId);

    // Extra features
    Task assignUser(Long taskId, User user);
    Task updateTaskStatus(Long taskId, TaskStatus status);
    List<Task> getTasksByProject(Long projectId);
    List<Task> getTasksByUser(Long userId);
}
