package com.codercultrera.ChatApp.service;

import com.codercultrera.ChatApp.domain.Channel;
import com.codercultrera.ChatApp.domain.Message;
import com.codercultrera.ChatApp.domain.User;
import com.codercultrera.ChatApp.dto.ChannelDTO;
import com.codercultrera.ChatApp.dto.MessageDTO;
import com.codercultrera.ChatApp.dto.UserDTO;
import com.codercultrera.ChatApp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepo;
    private final ChannelService channelService;
    private final UserService userService;

    public MessageService(MessageRepository messageRepo, ChannelService channelService, UserService userService) {
        this.messageRepo = messageRepo;
        this.channelService = channelService;
        this.userService = userService;
    }

    public void save(Message message) {
        messageRepo.save(message);
    }

    public List<Message> findByChannelId(Long channelId) {
        Channel theChannel = channelService.findById(channelId);
        return messageRepo.findByChannel(theChannel);
    }

    public MessageDTO recordMessage(Long i, MessageDTO messageDTO) {
        Message newMessage = new Message();
        newMessage.setMessageId(i);
        newMessage.setContent(messageDTO.getContent());
        newMessage.setMessageDate(LocalDateTime.now());

        User sender = userService.findById(messageDTO.getSender().getUserId());
        Channel channel = channelService.findById(messageDTO.getChannel().getChannelId());

        newMessage.setSender(sender);
        newMessage.setChannel(channel);

        save(newMessage);

        messageDTO.setMessageId(newMessage.getMessageId());
        messageDTO.setMessageDate(newMessage.getMessageDate());
        messageDTO.setContent(newMessage.getContent());
        messageDTO.setSender(new UserDTO(sender.getUserId(), sender.getUsername(), sender.getName()));
        messageDTO.setChannel(new ChannelDTO(channel.getChannelId(), channel.getChannelName()));
        return messageDTO;
    }



}
