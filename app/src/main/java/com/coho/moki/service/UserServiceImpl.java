package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.api.UserAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.data.remote.ShipFromResponseData;
import com.coho.moki.data.remote.UserFollowResponseData;
import com.coho.moki.data.remote.UserInfoResponseData;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public void getUserFollow(String token, String userId, Integer index, Integer count, String type, final ResponseListener<ArrayList<UserFollowResponseData>> listener) {
        Map<String, Object> data = new HashMap<>();
        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.USERID_TAG, userId);
        data.put(AppConstant.INDEX_TAG, index);
        data.put(AppConstant.COUNT_TAG, count);

        UserAPI service = ServiceGenerator.createService(UserAPI.class);
        Call<BaseResponse<ArrayList<UserFollowResponseData>>> call;
        if (type == AppConstant.FOLLOWING) {
            call =  service.getUserFollowing(data);
        } else {
            call =  service.getUserFollowed(data);
        }
        call.enqueue(new Callback<BaseResponse<ArrayList<UserFollowResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<UserFollowResponseData>>> call, Response<BaseResponse<ArrayList<UserFollowResponseData>>> response) {
                BaseResponse<ArrayList<UserFollowResponseData>> bodyResponse = response.body();

                if (bodyResponse == null) {
                    Utils.toastShort(BaseApp.getContext(), AppConstant.NO_FETCH_DATA);
                    listener.onFailure(AppConstant.NO_FETCH_DATA);
                    return;
                }

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        Utils.toastShort(BaseApp.getContext(), AppConstant.UNAUTHENTICATED);
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        Utils.toastShort(BaseApp.getContext(), AppConstant.NO_FETCH_DATA);
                        listener.onFailure(AppConstant.NO_FETCH_DATA);
                    }
                    return;
                }

                if (bodyResponse.getCode() != ResponseCode.OK.code) {
                    Utils.toastShort(BaseApp.getContext(), bodyResponse.getMessage());
                    listener.onFailure(bodyResponse.getMessage());
                    return;
                }

                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<UserFollowResponseData>>> call, Throwable t) {
                listener.onFailure(t.getMessage() + " get user follow");
            }

        });
    }

    @Override
    public void getUserShipFrom(int level, int parentId, final ResponseListener<List<ShipFromResponseData>> listener) {
        Map<String, Object> data = new HashMap<>();
        data.put(AppConstant.LEVEL_TAG, level);

        if (parentId != -1){
            data.put(AppConstant.PARENT_ID_TAG, parentId);
        }

        UserAPI service = ServiceGenerator.createService(UserAPI.class);
        Call<BaseResponse<List<ShipFromResponseData>>> call =  service.callGetListShipFrom(data);

        call.enqueue(new Callback<BaseResponse<List<ShipFromResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ShipFromResponseData>>> call, Response<BaseResponse<List<ShipFromResponseData>>> response) {

                BaseResponse<List<ShipFromResponseData>> bodyResponse = response.body();

                Log.d("trungship", response.body().getMessage());

                if (bodyResponse == null) {
                    Utils.toastShort(BaseApp.getContext(), AppConstant.NO_FETCH_DATA);
                    listener.onFailure(AppConstant.NO_FETCH_DATA);
                    return;
                }

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        Utils.toastShort(BaseApp.getContext(), AppConstant.UNAUTHENTICATED);
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        Utils.toastShort(BaseApp.getContext(), AppConstant.NO_FETCH_DATA);
                        listener.onFailure(AppConstant.NO_FETCH_DATA);
                    }
                    return;
                }

                if (bodyResponse.getCode() != ResponseCode.OK.code) {
                    Utils.toastShort(BaseApp.getContext(), bodyResponse.getMessage());
                    listener.onFailure(bodyResponse.getMessage());
                    return;
                }

                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<List<ShipFromResponseData>>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });


    }


}
