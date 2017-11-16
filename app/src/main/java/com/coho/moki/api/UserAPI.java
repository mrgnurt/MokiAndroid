package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.UserInfoResponseData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nguyenthanhtung on 15/11/2017.
 */

public interface UserAPI {
    @POST("/users/detail")
    Call<BaseResponse<UserInfoResponseData>> getUserInfo(@Body Map<String, String> data);
}
