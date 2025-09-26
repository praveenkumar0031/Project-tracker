package dev.project.project_manager.Services.ActivityLog;



import dev.project.project_manager.models.ActivityLog;
import dev.project.project_manager.Repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityLogServices implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogServices(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @Override
    public ActivityLog createLog(ActivityLog log) {
        log.setCreatedAt(LocalDateTime.now()); // auto timestamp
        return activityLogRepository.save(log);
    }

    @Override
    public Optional<ActivityLog> getLogById(Long id) {
        return activityLogRepository.findById(id);
    }

    @Override
    public List<ActivityLog> getAllLogs() {
        return activityLogRepository.findAll();
    }

    @Override
    public void deleteLog(Long id) {
        if (!activityLogRepository.existsById(id)) {
            throw new RuntimeException("Log not found with id: " + id);
        }
        activityLogRepository.deleteById(id);
    }

    // 🔹 Extra Queries
    @Override
    public List<ActivityLog> getLogsByProject(Long projectId) {
        return activityLogRepository.findByProjectId(projectId);
    }

    @Override
    public List<ActivityLog> getLogsByTask(Long taskId) {
        return activityLogRepository.findByTaskId(taskId);
    }

    @Override
    public List<ActivityLog> getLogsByUser(Long userId) {
        return activityLogRepository.findByUserId(userId);
    }

    @Override
    public List<ActivityLog> getLogsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return activityLogRepository.findByCreatedAtBetween(start, end);
    }
}

