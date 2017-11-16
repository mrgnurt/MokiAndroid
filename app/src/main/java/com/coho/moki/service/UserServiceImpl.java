package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.ProductAPI;
import com.coho.moki.api.UserAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ConversationResponseData;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.UserInfoResponseData;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguyenthanhtung on 15/11/2017.
 */

public class UserServiceImpl implements UserService {
    @Inject
    public UserServiceImpl(){
    }

    @Override
    public void getUserInfo(String token, String userId,final  ResponseListener<UserInfoResponseData> listener) {
        Map<String, String> data = new HashMap<>();
        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.USERID_TAG, userId);

        UserAPI service = ServiceGenerator.createService(UserAPI.class);
        Call<BaseResponse<UserInfoResponseData>> call =  service.getUserInfo(data);
        call.enqueue(new Callback<BaseResponse<UserInfoResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserInfoResponseData>> call, Response<BaseResponse<UserInfoResponseData>> response) {
                BaseResponse<UserInfoResponseData> bodyResponse = response.body();

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

                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<UserInfoResponseData>> call, Throwable t) {
                listener.onFailure(t.getMessage() + " get user info");
            }
        });
    }
}
