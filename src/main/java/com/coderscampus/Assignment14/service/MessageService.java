package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.repository.MessageRepository;
import com.coderscampus.Assignment14.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Message save(Message message) {
        return messageRepo.save(message);
    }

    public List<Message> findAll() {
        return messageRepo.findAll();
    }
}
