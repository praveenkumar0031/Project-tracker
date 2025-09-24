package dev.project.project_manager.models;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.util.*;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    private String role_name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}