package com.coderscampus.Assignment14.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;
    @Column(length = 100)
    private String channelName;
    @ManyToMany(mappedBy = "channels")
    private List<User> users = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    private List<Message> messages = new ArrayList<>();
}
