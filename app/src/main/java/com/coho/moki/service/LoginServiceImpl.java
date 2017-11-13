package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.LoginAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.LoginResponseData;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Pack200;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trung on 05/10/2017.
 */

public class LoginServiceImpl implements LoginService{

    @Inject
    public LoginServiceImpl(){}

    public void login(String phoneNumber, String password, final ResponseListener<LoginResponseData> listener){

        Map<String, String> dataLogin = new HashMap<>();

        dataLogin.put(AppConstant.PHONE_NUMBER_TAG, phoneNumber);
        dataLogin.put(AppConstant.PASSWORD_TAG, password);

        LoginAPI service = ServiceGenerator.createService(LoginAPI.class);

        Call<BaseResponse<LoginResponseData>> call = service.callNormalLogin(dataLogin);
        call.enqueue(new Callback<BaseResponse<LoginResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginResponseData>> call, Response<BaseResponse<LoginResponseData>> response) {
                int code = response.body().getCode();

                if (code == ResponseCode.OK.code){
                    listener.onSuccess(response.body().getData());
                }
                else{
                    listener.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LoginResponseData>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
