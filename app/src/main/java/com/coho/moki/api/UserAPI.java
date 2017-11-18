package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.UserFollowResponseData;
import com.coho.moki.data.remote.UserInfoResponseData;

import java.util.ArrayList;
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

    @POST("/users/following")
    Call<BaseResponse<ArrayList<UserFollowResponseData>>> getUserFollowing(@Body Map<String, Object> data);

    @POST("/users/followed")
    Call<BaseResponse<ArrayList<UserFollowResponseData>>> getUserFollowed(@Body Map<String, Object> data);
}
