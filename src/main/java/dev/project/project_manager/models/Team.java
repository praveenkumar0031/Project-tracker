package dev.project.project_manager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;

    @NotNull
    private Long leaderid;

    @ElementCollection
    @CollectionTable(
            name = "team_members",
            joinColumns = @JoinColumn(name = "team_id")
    )
    @Column(name = "member_id")
    private Set<Long> members = new HashSet<>();

}
