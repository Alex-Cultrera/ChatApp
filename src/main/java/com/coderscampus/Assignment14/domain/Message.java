package com.coderscampus.Assignment14.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private LocalDateTime messageDate;
    private String username;
    @Column(length = 625)
    private String content;
    @ManyToOne
    @JoinColumn(name="channel_id")
    private Channel channel;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", messageDate=" + messageDate +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", channel=" + channel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId) && Objects.equals(messageDate, message.messageDate) && Objects.equals(username, message.username) && Objects.equals(content, message.content) && Objects.equals(channel, message.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, messageDate, username, content, channel);
    }
}
