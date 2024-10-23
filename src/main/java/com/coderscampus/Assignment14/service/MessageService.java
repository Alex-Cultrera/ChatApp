package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.repository.ChannelRepository;
import com.coderscampus.Assignment14.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepo;
    private final ChannelRepository channelRepository;
    private final ChannelService channelService;

    public MessageService(MessageRepository messageRepo, ChannelRepository channelRepository, ChannelService channelService) {
        this.messageRepo = messageRepo;
        this.channelRepository = channelRepository;
        this.channelService = channelService;
    }

    public Message save(Message message) {
        System.out.println("Saving message: " + message);
        return messageRepo.save(message);
    }

    public List<Message> findAll() {
        return messageRepo.findAll();
    }

    public List<Message> findByChannelId(Long channelId) {
        Channel theChannel = channelService.findById(channelId);
        return messageRepo.findByChannelOrderByMessageDateAsc(theChannel);
    }
}
