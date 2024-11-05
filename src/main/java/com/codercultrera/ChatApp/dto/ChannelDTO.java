package com.codercultrera.ChatApp.dto;



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
