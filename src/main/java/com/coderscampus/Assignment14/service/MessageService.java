package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepo;
    private final ChannelService channelService;

    public MessageService(MessageRepository messageRepo, ChannelService channelService) {
        this.messageRepo = messageRepo;
        this.channelService = channelService;
    }

    public void save(Message message) {
        System.out.println("Saving message: " + message);
        messageRepo.save(message);
    }

    public List<Message> findByChannelId(Long channelId) {
        Channel theChannel = channelService.findById(channelId);
        return messageRepo.findByChannel(theChannel);
    }

}
