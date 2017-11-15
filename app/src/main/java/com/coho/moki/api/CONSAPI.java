package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ListConversationResponceData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by trung on 14/11/2017.
 */

public interface CONSAPI {

    @POST("/get_list_converation")
    Call<BaseResponse<ListConversationResponceData>> getListConversation(@Body Map<String, String> tag);
}
