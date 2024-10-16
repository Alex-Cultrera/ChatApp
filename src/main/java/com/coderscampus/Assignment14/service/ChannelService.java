package com.coderscampus.Assignment14.service;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.repository.ChannelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelService {

    private final ChannelRepository channelRepo;

    public ChannelService(ChannelRepository channelRepo) {
        this.channelRepo = channelRepo;
    }

    public Channel findById(Long channelId) {
        Optional<Channel> channelOpt = channelRepo.findById(channelId);
        return channelOpt.orElse(new Channel());
    }

    public void createDefaultUserChannels(User user) {
        Channel defaultChannel = new Channel();
        defaultChannel.setChannelName("General");
        defaultChannel.getUsers().add(user);
        user.getChannels().add(defaultChannel);
        channelRepo.save(defaultChannel);
    }

}
