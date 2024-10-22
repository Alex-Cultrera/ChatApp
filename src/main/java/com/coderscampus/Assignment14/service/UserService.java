package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final HttpSession session;

    public UserService(UserRepository userRepo, HttpSession session) {
        this.userRepo = userRepo;
        this.session = session;
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
