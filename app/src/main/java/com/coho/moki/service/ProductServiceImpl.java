package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.ProductAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.CheckNewItemResponse;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.data.remote.UserProductResponseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trung on 17/10/2017.
 */

public class ProductServiceImpl implements ProductService {

    @Override
    public void getListProduct(String token, String categoryId, String campaignId, String lastId,
                               String index, int count, final ResponseListener<GetListProductResponceData> listener) {
        Map<String, Object> data = new HashMap<>();
        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.CATEGORYID_TAG, categoryId);
        data.put(AppConstant.CAMPAIGNID_TAG, campaignId);
        data.put(AppConstant.LASTID_TAG, lastId);
        data.put(AppConstant.INDEX_TAG, index);
        data.put(AppConstant.COUNT_TAG, ((Integer)count).toString());

        ProductAPI service = ServiceGenerator.createService(ProductAPI.class);
        Call<BaseResponse<GetListProductResponceData>> call =  service.callGetListProduct(data);
        Log.d("trung","abc");
        call.enqueue(new Callback<BaseResponse<GetListProductResponceData>>() {
            @Override
            public void onResponse(Call<BaseResponse<GetListProductResponceData>> call, Response<BaseResponse<GetListProductResponceData>> response) {

                int code = response.body().getCode();
                if (code == ResponseCode.OK.code){

                    listener.onSuccess(response.body().getData());
                }
                else{

                    listener.onFailure(response.body().getMessage() + " get list product");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<GetListProductResponceData>> call, Throwable t) {
                listener.onFailure(t.getMessage() + " get list product");

            }
        });
    }

    @Override
    public void getProductOfUser(String token, int index, int count, String userId, String keyword, String categoryId, final ResponseListener<ArrayList<UserProductResponseData>> listener) {
        Map<String, Object> data = new HashMap<>();
        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.INDEX, index);
        data.put(AppConstant.COUNT_TAG, count);
        data.put(AppConstant.USERID_TAG, userId);
        data.put(AppConstant.KEYWORD_TAG, keyword);
        data.put(AppConstant.CATEGORY_TAG, categoryId);

        ProductAPI service = ServiceGenerator.createService(ProductAPI.class);
        Call<BaseResponse<ArrayList<UserProductResponseData>>> call =  service.callGetUserProduct(data);
        call.enqueue(new Callback<BaseResponse<ArrayList<UserProductResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<UserProductResponseData>>> call, Response<BaseResponse<ArrayList<UserProductResponseData>>> response) {
                int code = response.body().getCode();
                if (code == ResponseCode.OK.code){

                    listener.onSuccess(response.body().getData());
                }
                else{

                    listener.onFailure(response.body().getMessage() + " get list product");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<UserProductResponseData>>> call, Throwable t) {
                listener.onFailure(t.getMessage() + " get list product");
            }

        });
    }

    @Override
    public void getMyLikeProduct(String token, int index, int count, final ResponseListener<List<MyLikeResponseData>> listener) {

        Map<String, Object> data = new HashMap<>();
        data.put(AppConstant.TOKEN, token);
        data.put(AppConstant.INDEX_TAG, index);
        data.put(AppConstant.COUNT_TAG, count);

        ProductAPI service = ServiceGenerator.createService(ProductAPI.class);
        Call<BaseResponse<List<MyLikeResponseData>>> call = service.callGetMyLikeProduct(data);
        call.enqueue(new Callback<BaseResponse<List<MyLikeResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<MyLikeResponseData>>> call, Response<BaseResponse<List<MyLikeResponseData>>> response) {
                BaseResponse<List<MyLikeResponseData>> bodyResponse = response.body();

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
            public void onFailure(Call<BaseResponse<List<MyLikeResponseData>>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });



    }

    @Override
    public void checkNewItem(String lastId, String categoryId, final ResponseListener<CheckNewItemResponse> listener) {

        Map<String, String> data = new HashMap<>();
        data.put(AppConstant.LASTID_TAG, lastId);

        if (!categoryId.equals("") && categoryId != null){
            data.put(AppConstant.CAMPAIGNID_TAG, categoryId);
        }

        ProductAPI service = ServiceGenerator.createService(ProductAPI.class);
        Call<BaseResponse<CheckNewItemResponse>> call = service.callCheckNewItem(data);
        call.enqueue(new Callback<BaseResponse<CheckNewItemResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<CheckNewItemResponse>> call, Response<BaseResponse<CheckNewItemResponse>> response) {
                BaseResponse<CheckNewItemResponse> bodyResponse = response.body();

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
            public void onFailure(Call<BaseResponse<CheckNewItemResponse>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }
}
