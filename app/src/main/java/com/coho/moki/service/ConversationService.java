package com.coho.moki.service;

import com.coho.moki.data.remote.ConversationResponseData;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public interface ConversationService {

    public void loadConversationDetail(String token, String partnerId, String productId, int index, int count, ResponseListener<ConversationResponseData> listener);
}
