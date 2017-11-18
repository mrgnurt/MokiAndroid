package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.CONSAPI;
import com.coho.moki.api.ConversationAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ConversationResponseData;
import com.coho.moki.data.remote.ListConversationResponceData;
import com.coho.moki.data.remote.SearchProductResponseData;
import com.squareup.picasso.Downloader;

import java.util.HashMap;
import java.util.List;
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

    @Override
    public void loadConversations(String token, int index, int count, final ResponseListener<ListConversationResponceData> listener) {
        Map<String, String> data = new HashMap<>();

        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.INDEX_TAG, index + "");
        data.put(AppConstant.COUNT_TAG, count + "");

        ConversationAPI consAPI = ServiceGenerator.createService(ConversationAPI.class);
        Call<BaseResponse<ListConversationResponceData>> call =  consAPI.loadConversations(data);

        call.enqueue(new Callback<BaseResponse<ListConversationResponceData>>() {
            @Override
            public void onResponse(Call<BaseResponse<ListConversationResponceData>> call, Response<BaseResponse<ListConversationResponceData>> response) {
                BaseResponse<ListConversationResponceData> bodyResponse = response.body();

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        listener.onFailure(AppConstant.NO_FETCH_DATA);
                    }
                    return;
                }

                if (bodyResponse == null) {
                    listener.onFailure(AppConstant.NO_FETCH_DATA);
                    return;
                }

                if (bodyResponse.getCode() != ResponseCode.OK.code) {
                    listener.onFailure(bodyResponse.getMessage());
                    return;
                }

                listener.onSuccess(bodyResponse.getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<ListConversationResponceData>> call, Throwable t) {
                listener.onFailure(AppConstant.CALL_ERR);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void setReadMessage(String token, String partnerId, String productId, final ResponseListener listener) {
        Map<String, String> data = new HashMap<>();

        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.PARTNER_ID_CHAT_TAG, partnerId);
        data.put(AppConstant.PRODUCT_ID_CHAT_TAG, productId);

        ConversationAPI consAPI = ServiceGenerator.createService(ConversationAPI.class);
        Call<BaseResponse> call =  consAPI.setReadMessage(data);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse bodyResponse = response.body();

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        listener.onFailure(AppConstant.NO_FETCH_DATA);
                    }
                    return;
                }

                if (bodyResponse == null) {
                    listener.onFailure(AppConstant.NO_FETCH_DATA);
                    return;
                }

                if (bodyResponse.getCode() != ResponseCode.OK.code) {
                    listener.onFailure(bodyResponse.getMessage());
                    return;
                }

                listener.onSuccess(bodyResponse.getData());
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                listener.onFailure(AppConstant.CALL_ERR);
                t.printStackTrace();
            }
        });
    }
}
