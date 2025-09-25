package dev.project.project_manager.Repository;

import dev.project.project_manager.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
