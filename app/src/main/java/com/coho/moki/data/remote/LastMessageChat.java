package com.coho.moki.data.remote;

/**
 * Created by trung on 14/11/2017.
 */

public class LastMessageChat {

    public String message;
    public String created;
    public String unRead;

    public LastMessageChat(String message, String created, String unRead) {
        this.message = message;
        this.created = created;
        this.unRead = unRead;
    }
}
