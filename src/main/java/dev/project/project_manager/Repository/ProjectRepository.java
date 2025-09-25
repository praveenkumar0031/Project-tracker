package dev.project.project_manager.Repository;

import dev.project.project_manager.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}