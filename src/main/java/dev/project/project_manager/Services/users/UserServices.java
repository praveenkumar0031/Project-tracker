package dev.project.project_manager.Services.users;

import dev.project.project_manager.Repository.UserRepository;
import dev.project.project_manager.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices implements UserService {

    private final UserRepository userRepository;


    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;

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
    public Optional<User> getUserByTeamId(Long id){
        return userRepository.findByTeamid(id);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User assignTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getTeamid() != null) {
            throw new RuntimeException("User already assigned to a team");
        }
        user.setTeamid(teamId);
        return userRepository.save(user);
    }

    @Override
    public User removeFromTeam(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setTeamid(null);
        return userRepository.save(user);
    }
}
