package com.eardh.wechat.model.pojo;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String password;
    private String nickname;
    private String avatarUrl;
    private int state;

    public User() {
    }

    public User(String userId, String username, String password, String avatarUrl) {
        this.userId = userId;
        this.nickname = username;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}