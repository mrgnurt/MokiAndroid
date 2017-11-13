package com.coho.moki.data.remote;

import com.coho.moki.data.model.User;

/**
 * Created by trung on 05/10/2017.
 */

public class LoginResponseData {

    private String id;
    private String username;
    private String token;
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
