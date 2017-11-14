package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ConversationResponseData;

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
}
