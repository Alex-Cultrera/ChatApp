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

    public void createDefaultUserChannels(User user) {
        Channel defaultChannel = new Channel();
        defaultChannel.setChannelName("General");
        defaultChannel.getUsers().add(user);
        user.getChannels().add(defaultChannel);
        channelRepo.save(defaultChannel);
    }

    public Channel createChannel(Channel channel, List<User> users) {
        Channel newChannel = new Channel();
        newChannel.setChannelName(channel.getChannelName());
        newChannel.setUsers(users);
        for (User user : users) {
            user.getChannels().add(newChannel);
        }
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
