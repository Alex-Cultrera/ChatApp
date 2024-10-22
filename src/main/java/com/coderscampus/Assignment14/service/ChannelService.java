package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.repository.ChannelRepository;
import com.coderscampus.Assignment14.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {

    private final ChannelRepository channelRepo;
    private final UserService userService;
    private final UserRepository userRepo;

    public ChannelService(ChannelRepository channelRepo, UserService userService, UserRepository userRepo) {
        this.channelRepo = channelRepo;
        this.userService = userService;
        this.userRepo = userRepo;
    }

//    public void saveChannelToUser(Channel channel, Long userId) {
//        User existingUser = userService.findById(userId);
//        channel.getUsers().add(existingUser);
//        existingUser.getChannels().add(channel);
//        channelRepo.save(channel);
//        userService.save(existingUser);
//    }

    public void createDefaultUserChannels(User user) {
        Channel defaultChannel = new Channel();
        defaultChannel.setChannelName("General");
        defaultChannel.getUsers().add(user);
        user.getChannels().add(defaultChannel);
        channelRepo.save(defaultChannel);
    }

    public Channel createChannel(Channel channel, User user) {
        Channel newChannel = new Channel();
        newChannel.setChannelName(channel.getChannelName());
        newChannel.getUsers().add(user);
        user.getChannels().add(newChannel);
        return channelRepo.save(newChannel);
    }

      public List<Channel> findAll() {
        return channelRepo.findAll();
    }

    public Channel findById(Long channelId) {
        Optional<Channel> channelOpt = channelRepo.findById(channelId);
        return channelOpt.orElse(new Channel());
    }

    public Channel save(Channel channel) {
        return channelRepo.save(channel);
    }

    public boolean validateChannelName(String channelName) {
        Channel channel = channelRepo.findByChannelName(channelName);
        return channel != null;
    }

}
