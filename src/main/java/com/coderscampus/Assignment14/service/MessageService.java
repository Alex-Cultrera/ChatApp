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

    public MessageService(MessageRepository messageRepo, ChannelRepository channelRepository) {
        this.messageRepo = messageRepo;
        this.channelRepository = channelRepository;
    }

    public Message save(Message message) {
        return messageRepo.save(message);
    }

    public List<Message> findAll() {
        return messageRepo.findAll();
    }

    public List<Message> findByChannelId(Long channelId) {
        Channel channel;
        channel = channelRepository.findById(channelId).orElse(null);
        return messageRepo.findByChannelOrderByMessageDateAsc(channel);
    }
}
