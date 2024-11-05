package com.codercultrera.ChatApp.dto;



public class UserDTO {
    private Long userId;
    private String username;
    private String name;

    public UserDTO(Long userId, String username, String name) {
        this.userId = userId;
        this.username = username;
        this.name = name;
    }

    public UserDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
