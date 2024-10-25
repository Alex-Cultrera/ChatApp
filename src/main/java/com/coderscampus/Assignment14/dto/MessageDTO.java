package com.coderscampus.Assignment14.dto;


import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;


public class MessageDTO {
    private Long messageId;
    private LocalDateTime messageDate;
    private String content;
    private UserDTO sender;
    private ChannelDTO channel;


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public LocalDateTime getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "messageId=" + messageId +
                ", messageDate=" + messageDate +
                ", content='" + content + '\'' +
                ", sender=" + sender +
                ", channel=" + channel +
                '}';
    }
}
