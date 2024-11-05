package com.codercultrera.ChatApp.service;

import com.codercultrera.ChatApp.domain.User;
import com.codercultrera.ChatApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public void update(User existingUser, User user) {
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setName(user.getName());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }
            user = existingUser;
            save(user);
        }
    }

    public boolean validateUsername(String username) {
        User user = userRepo.findByUsername(username);
        return user != null;
    }

}
