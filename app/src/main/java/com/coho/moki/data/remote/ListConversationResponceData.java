package com.coho.moki.data.remote;

import com.coho.moki.data.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 14/11/2017.
 */

public class ListConversationResponceData {

    public List<ChatConsersation> chats;
    public int numNewMessage;

    public ListConversationResponceData(List<ChatConsersation> chats, int numNewMessage) {
        this.chats = chats;
        this.numNewMessage = numNewMessage;
    }



}
