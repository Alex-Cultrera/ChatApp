package com.coderscampus.Assignment14.dto;

import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.domain.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ChannelDTO {
    private Long channelId;
    private String channelName;

    public ChannelDTO(Long channelId, String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }

    public ChannelDTO() {

    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    @Override
    public String toString() {
        return "ChannelDTO{" +
                "channelId=" + channelId +
                ", channelName='" + channelName + '\'' +
                '}';
    }
}
