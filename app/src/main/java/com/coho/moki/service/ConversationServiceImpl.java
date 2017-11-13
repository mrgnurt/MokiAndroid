package com.coho.moki.service;

import com.coho.moki.api.ConversationAPI;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ConversationResponseData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public class ConversationServiceImpl implements ConversationService {
    @Override
    public void loadConversationDetail(String token, String partnerId, String productId, int index, int count, final ResponseListener<ConversationResponseData> listener) {

        Map<String, String> consParam = new HashMap<>();
        consParam.put("partnerId", partnerId);
        consParam.put("productId", productId);
        consParam.put("index", index + "");
        consParam.put("count", count + "");

        ConversationAPI service = ServiceGenerator.createService(ConversationAPI.class);
        Call<BaseResponse<ConversationResponseData>> call = service.getConversationDetail(consParam);
        call.enqueue(new Callback<BaseResponse<ConversationResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<ConversationResponseData>> call, Response<BaseResponse<ConversationResponseData>> response) {
                int code = response.body().getCode();

                if (code == ResponseCode.OK.code){
                    listener.onSuccess(response.body().getData());
                }
                else{
                    listener.onFailure(response.body().getMessage() + "login");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ConversationResponseData>> call, Throwable t) {
                listener.onFailure(t.getMessage() + "login");
            }
        });
    }
}
