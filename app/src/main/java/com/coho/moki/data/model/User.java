package com.coho.moki.data.model;

import com.coho.moki.data.constant.ResponseCode;

/**
 * Created by trung on 05/10/2017.
 */

public class User {

    private String userId;

    private String username;

    private String token;

    private String avatarUrl;

    public User(String userId, String username, String token, String avatarUrl) {
        this.userId = userId;
        this.username = username;
        this.token = token;
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
