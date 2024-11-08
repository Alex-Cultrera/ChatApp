package com.codercultrera.ChatApp.service;

import com.codercultrera.ChatApp.domain.MyUserDetails;
import com.codercultrera.ChatApp.domain.User;
import com.codercultrera.ChatApp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public MyUserDetailsService (UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new MyUserDetails(user);
    }

}
