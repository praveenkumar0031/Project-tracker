package dev.project.project_manager.Repository;


import dev.project.project_manager.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByLeaderid(Long leaderId);

    @Query("SELECT t.members FROM Team t WHERE t.id = :teamId")
    Set<Long> findAllMembersByTeamId(Long teamId);
    Optional<Team> findByName(String name);
}