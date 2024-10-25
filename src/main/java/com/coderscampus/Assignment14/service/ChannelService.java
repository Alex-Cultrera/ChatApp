package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepo;

    public ChannelService(ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    public void createDefaultUserChannels(User user) {
        Channel defaultChannel = new Channel();
        defaultChannel.setChannelName("General");
        defaultChannel.getUsers().add(user);
        user.getChannels().add(defaultChannel);
        channelRepo.save(defaultChannel);
    }

    public void getAllChannels(User user) {
        List<Channel> allChannels = channelRepo.findAll();
        for (Channel channel : allChannels) {
            channel.getUsers().add(user);
            user.getChannels().add(channel);
            channelRepo.save(channel);
        }
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
        return channelRepo.findChannelByChannelId(channelId);
    }

    public boolean validateChannelName(String channelName) {
        Channel channel = channelRepo.findByChannelName(channelName);
        return channel != null;
    }

    public Channel findByChannelName(String channelName) {
        return channelRepo.findByChannelName(channelName);
    }
}
