package dev.project.project_manager.Services;

import dev.project.project_manager.Repository.UserRepository;
import dev.project.project_manager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepo;

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User getUserById(long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }
}
