package dev.project.project_manager.Repository;

import dev.project.project_manager.models.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByUserId(Long userId);
    List<ActivityLog> findByTaskId(Long taskId);
    List<ActivityLog> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<ActivityLog> findByProjectId(Long projectId);
}