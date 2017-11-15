package com.coho.moki.data.remote;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public class Conversation {

    private String message;

    private int unread;

    private String created;

    private Sender sender;

    public Conversation(String message, int unread, String created, Sender sender) {
        this.message = message;
        this.unread = unread;
        this.created = created;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}

