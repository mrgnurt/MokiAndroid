package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.LoginResponseData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by trung on 06/10/2017.
 */

public interface LoginAPI {
    @POST("/login")
    Call<BaseResponse<LoginResponseData>> callNormalLogin(@Body Map<String, String> tag, @HeaderMap Map<String, String> tagHeader);

    @POST("devices/set-dev-token")
    Call<BaseResponse<String>> setDeviceToken(@Body Map<String, String> tag);
}
