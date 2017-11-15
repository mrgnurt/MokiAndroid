package com.coho.moki.service;

import com.coho.moki.api.CONSAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ListConversationResponceData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trung on 14/11/2017.
 */

public class CONSServiceImpl implements CONSService {

    @Override
    public void getListConversation(String token, String index, String count, final ResponseListener<ListConversationResponceData> listener) {

        Map<String, String> data = new HashMap<>();

        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.INDEX_TAG, index);
        data.put(AppConstant.COUNT_TAG, count);

        CONSAPI service = ServiceGenerator.createService(CONSAPI.class);
        Call<BaseResponse<ListConversationResponceData>> call =  service.getListConversation(data);

        call.enqueue(new Callback<BaseResponse<ListConversationResponceData>>() {
            @Override
            public void onResponse(Call<BaseResponse<ListConversationResponceData>> call, Response<BaseResponse<ListConversationResponceData>> response) {
                int code = response.body().getCode();

                if (code == ResponseCode.OK.code){
                    listener.onSuccess(response.body().getData());
                }
                else{
                    listener.onFailure(response.body().getMessage() + "getlistconversation");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ListConversationResponceData>> call, Throwable t) {
                listener.onFailure(t.getMessage() + "getlistconversation");
            }
        });
    }
}
