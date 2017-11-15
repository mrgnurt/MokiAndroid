package com.coho.moki.data.model;

import java.util.Date;

/**
 * Created by Khanh Nguyen on 10/22/2017.
 */

public class ProductChatItem {
    private int role;

    private String message;

    private String avatar;

    private Date sendAt;

    private String serverMsgId;

    public static int SENDER = 1;

    public static int RECEIVER = 2;

    public ProductChatItem(){
        role = SENDER;
        message = null;
        avatar = null;
        serverMsgId = null;
        sendAt = new Date();
    }

    public ProductChatItem(int role, String message, String avatar, Date sendAt) {
        this.role = role;
        this.message = message;
        this.avatar = avatar;
        this.sendAt = sendAt;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }

    public String getFormatDate() {
        return sendAt.toString();
    }

    public String getServerMsgId() {
        return serverMsgId;
    }

    public void setServerMsgId(String serverMsgId) {
        this.serverMsgId = serverMsgId;
    }
}
