package com.codercultrera.ChatApp.repository;

import com.codercultrera.ChatApp.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Channel findChannelByChannelId(Long channelId);
    Channel findByChannelName(String channelName);
}
