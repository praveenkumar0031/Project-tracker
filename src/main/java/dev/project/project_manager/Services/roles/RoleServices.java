package dev.project.project_manager.Services.roles;


import dev.project.project_manager.Repository.RoleRepository;
import dev.project.project_manager.Repository.UserRepository;
import dev.project.project_manager.models.Role;
import dev.project.project_manager.models.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServices implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServices(RoleRepository roleRepository,
                        UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long roleId, Role roleDetails) {
        return roleRepository.findById(roleId)
                .map(existing -> {
                    existing.setRolename(roleDetails.getRolename());
                    return roleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
    }

    @Override
    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new RuntimeException("Role not found with id: " + roleId);
        }
        roleRepository.deleteById(roleId);
    }

    // 🔹 Assign role to user
    @Override
    public Role assignRoleToUser(Long roleId, User user) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User dbUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        role.getUsers().add(dbUser);
        dbUser.getRoles().add(role);

        userRepository.save(dbUser);
        return roleRepository.save(role);
    }

    // 🔹 Remove role from user
    @Override
    public Role removeRoleFromUser(Long roleId, User user) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User dbUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        role.getUsers().remove(dbUser);
        dbUser.getRoles().remove(role);

        userRepository.save(dbUser);
        return roleRepository.save(role);
    }

    // 🔹 Get all users with a role
    @Override
    public Set<User> getUsersByRole(Long roleId) {
        return roleRepository.findById(roleId)
                .map(Role::getUsers)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
