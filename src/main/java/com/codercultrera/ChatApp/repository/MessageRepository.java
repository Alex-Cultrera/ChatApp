package com.codercultrera.ChatApp.repository;

import com.codercultrera.ChatApp.domain.Channel;
import com.codercultrera.ChatApp.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByChannel(Channel channel);
}
