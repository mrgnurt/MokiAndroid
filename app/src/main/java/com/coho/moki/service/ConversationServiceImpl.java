package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.ConversationAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ConversationResponseData;
import com.squareup.picasso.Downloader;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public class ConversationServiceImpl implements ConversationService {

    public static final String LOG_TAG = "ConversationServiceImpl";

    @Override
    public void loadConversationDetail(String token, String partnerId, String productId, int count, int index, final ResponseListener<ConversationResponseData> listener) {

        Map<String, String> consParam = new HashMap<>();
        consParam.put("token", token);
        consParam.put("partnerId", partnerId);
        consParam.put("index", index + "");
        consParam.put("count", count + "");
        consParam.put("productId", productId);

        ConversationAPI service = ServiceGenerator.createService(ConversationAPI.class);
        Call<BaseResponse<ConversationResponseData>> call = service.getConversationDetail(consParam);
        call.enqueue(new Callback<BaseResponse<ConversationResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<ConversationResponseData>> call, Response<BaseResponse<ConversationResponseData>> response) {
                BaseResponse<ConversationResponseData> bodyResponse = response.body();

                if (bodyResponse == null) {
                    listener.onFailure(AppConstant.NO_FETCH_DATA);
                    return;
                }

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        listener.onFailure(AppConstant.NO_FETCH_DATA);
                    }
                    return;
                }

                if (bodyResponse.getCode() != ResponseCode.OK.code) {
                    listener.onFailure(bodyResponse.getMessage());
                    return;
                }

                listener.onSuccess(bodyResponse.getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<ConversationResponseData>> call, Throwable t) {
                listener.onFailure(AppConstant.CALL_ERR);
                t.printStackTrace();
            }
        });
    }
}
