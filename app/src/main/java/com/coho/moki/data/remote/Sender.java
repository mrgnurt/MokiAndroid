package com.coho.moki.data.remote;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public class Sender {

    private String id;

    private String username;

    public Sender(String id, String username) {
        this.id = id;
        this.username = username;
    }

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
}
