package com.codercultrera.ChatApp.repository;

import com.codercultrera.ChatApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername (String username);

}


