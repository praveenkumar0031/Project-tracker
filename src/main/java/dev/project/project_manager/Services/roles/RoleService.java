package dev.project.project_manager.Services.roles;


import dev.project.project_manager.models.Role;
import dev.project.project_manager.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Role createRole(Role role);
    Role updateRole(Long roleId, Role roleDetails);
    Optional<Role> getRoleById(Long roleId);
    List<Role> getAllRoles();
    void deleteRole(Long roleId);

    // Extra
    Role assignRoleToUser(Long roleId, User user);
    Role removeRoleFromUser(Long roleId, User user);
    Set<User> getUsersByRole(Long roleId);
}
