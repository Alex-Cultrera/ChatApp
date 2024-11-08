package com.codercultrera.ChatApp.service;

import com.codercultrera.ChatApp.domain.User;
import com.codercultrera.ChatApp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


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

    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
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
