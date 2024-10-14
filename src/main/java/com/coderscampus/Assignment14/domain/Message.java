package com.coderscampus.Assignment14.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;
    private LocalDateTime messageDate;
    @ManyToOne
    @JoinColumn(name="channel_id")
    private Channel channel;


}
