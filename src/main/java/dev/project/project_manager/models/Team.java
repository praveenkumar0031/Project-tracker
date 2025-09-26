package dev.project.project_manager.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamname;


    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = false, unique = true)
    private User leader;


    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> members = new HashSet<>();

    @ManyToMany(mappedBy = "teams")
    private Set<Project> projects = new HashSet<>();
}