package dev.project.project_manager.Services.ActivityLog;


import dev.project.project_manager.models.ActivityLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityLogService {
    ActivityLog createLog(ActivityLog log);
    Optional<ActivityLog> getLogById(Long id);
    List<ActivityLog> getAllLogs();
    void deleteLog(Long id);

    // Extra features
    List<ActivityLog> getLogsByProject(Long projectId);
    List<ActivityLog> getLogsByTask(Long taskId);
    List<ActivityLog> getLogsByUser(Long userId);
    List<ActivityLog> getLogsBetweenDates(LocalDateTime start, LocalDateTime end);
}