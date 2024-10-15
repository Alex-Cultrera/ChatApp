package com.coderscampus.Assignment14.repository;

import com.coderscampus.Assignment14.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername (String username);

//    @Query("select u from User u"
//            + " left join fetch u.accounts"
//            + " left join fetch u.address")
//    Set<User> findAllUsersWithChannelsAndMessages();

}
