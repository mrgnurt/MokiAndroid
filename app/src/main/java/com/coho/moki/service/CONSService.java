package com.coho.moki.service;

import com.coho.moki.data.remote.ListConversationResponceData;

/**
 * Created by trung on 14/11/2017.
 */

public interface CONSService {

    void getListConversation(String token, String index, String count, ResponseListener<ListConversationResponceData> listener);
}
