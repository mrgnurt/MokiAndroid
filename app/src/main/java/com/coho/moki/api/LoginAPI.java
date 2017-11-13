package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.LoginResponseData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by trung on 06/10/2017.
 */

public interface LoginAPI {
    @POST("/login")
    Call<BaseResponse<LoginResponseData>> callNormalLogin(@Body Map<String, String> tag);
}
