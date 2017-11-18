package com.coho.moki.data.remote;

/**
 * Created by nguyenthanhtung on 16/11/2017.
 */

public class UserFollowResponseData {
    private String id;
    private String username;
    private String avatar;
    private Integer followed;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getFollowed() {
        return followed;
    }

    public void setFollowed(Integer followed) {
        this.followed = followed;
    }
}
