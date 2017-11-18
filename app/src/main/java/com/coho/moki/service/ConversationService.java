package com.coho.moki.service;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ConversationResponseData;
import com.coho.moki.data.remote.ListConversationResponceData;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public interface ConversationService {

    void loadConversationDetail(String token, String partnerId, String productId, int count, int limit, ResponseListener<ConversationResponseData> listener);

    void loadConversations(String token, int index, int count, ResponseListener<ListConversationResponceData> listener);

    void setReadMessage(String token, String partnerId, String productId, ResponseListener<BaseResponse> listener);
}
