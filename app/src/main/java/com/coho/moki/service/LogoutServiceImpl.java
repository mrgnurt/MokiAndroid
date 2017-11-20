package com.coho.moki.service;

import com.coho.moki.api.ConversationAPI;
import com.coho.moki.api.LoginAPI;
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
 * Created by tonquangtu on 18/11/2017.
 */

public class LogoutServiceImpl implements LogoutService {

    public void logout(String token, final ResponseListener listener) {
        Map<String, String> data = new HashMap<>();

        data.put(AppConstant.TOKEN_TAG, token);
        LoginAPI loginAPI = ServiceGenerator.createService(LoginAPI.class);
        Call<BaseResponse> call =  loginAPI.logout(data);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse<ListConversationResponceData> bodyResponse = response.body();

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        listener.onFailure(AppConstant.CAN_NOT_LOGOUT);
                    }
                    return;
                }

                if (bodyResponse == null) {
                    listener.onFailure(AppConstant.CAN_NOT_LOGOUT);
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
