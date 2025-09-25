package dev.project.project_manager.Services.users;

import dev.project.project_manager.models.User;

import java.util.*;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);

    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();

    User assignTeam(Long userId, Long teamId);
    User removeFromTeam(Long userId);
}