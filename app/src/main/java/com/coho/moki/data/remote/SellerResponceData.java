package com.coho.moki.data.remote;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by trung on 17/10/2017.
 */

public class SellerResponceData {
    private String id;
    private String userName;
    private String avatar;

    public SellerResponceData(String id, String userName, String avatar) {
        this.id = id;
        this.userName = userName;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static Type getType() {
        return new TypeToken<SellerResponceData>() {
        }.getType();
    }
}
