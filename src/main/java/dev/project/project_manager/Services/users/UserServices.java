package dev.project.project_manager.Services.users;

import dev.project.project_manager.Repository.TeamRepository;
import dev.project.project_manager.Repository.UserRepository;
import dev.project.project_manager.models.Team;
import dev.project.project_manager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices implements UserService {

    private  UserRepository userRepository;
    private  TeamRepository teamRepository;

    public UserServices(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(user.getUsername());
                    existing.setEmail(user.getEmail());
                    return userRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User assignTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (user.getTeam() != null) {
            throw new RuntimeException("User already assigned to a team");
        }
        user.setTeam(team);
        return userRepository.save(user);
    }

    @Override
    public User removeFromTeam(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setTeam(null);
        return userRepository.save(user);
    }
}
