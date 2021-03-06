package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ConversationResponseData;
import com.coho.moki.data.remote.ListConversationResponceData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public interface ConversationAPI {

    @POST("conversations/detail")
    Call<BaseResponse<ConversationResponseData>> getConversationDetail(@Body Map<String, String> tag);

    @POST("conversations")
    Call<BaseResponse<ListConversationResponceData>> loadConversations(@Body Map<String, String> tag);

    @POST("conversations/set-read-messages")
    Call<BaseResponse> setReadMessage(@Body Map<String, String> tag);
}
