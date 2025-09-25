package dev.project.project_manager.Repository;

import dev.project.project_manager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}