package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.ProductAPI;
import com.coho.moki.api.ProductDetailAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.LikeResponseData;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;
import com.coho.moki.data.remote.UserInfoResponseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Khanh Nguyen on 11/3/2017.
 */

public class ProductDetailServiceImpl implements ProductDetailService {

    private static final String LOG_TAG = "PdDetailServiceImpl";

    @Inject
    public ProductDetailServiceImpl() {}

    @Override
    public void getProductDetailRemote(String token, String productId, final ResponseListener<ProductDetailResponse> listener) {
        Map<String, String> data = new HashMap<>();
        data.put(AppConstant.TOKEN, token);
        data.put(AppConstant.ID, productId);
        ProductDetailAPI service = ServiceGenerator.createService(ProductDetailAPI.class);
        Call<BaseResponse<ProductDetailResponse>> call = service.getProductDetail(data);
        call.enqueue(new Callback<BaseResponse<ProductDetailResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProductDetailResponse>> call, Response<BaseResponse<ProductDetailResponse>> response) {

                if (response == null) {
                    Log.d(LOG_TAG, "response == null");
                } else if (response.body() == null) {
                    Log.d(LOG_TAG, "response body == null");
                }

                int code = response.body().getCode();

                if (code == ResponseCode.OK.code){
                    listener.onSuccess(response.body().getData());
                }
                else{
                    listener.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProductDetailResponse>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getProductCommentRemote(String productId, final ResponseListener<List<ProductCommentResponse>> listener) {
        Map<String, String> data = new HashMap<>();
        data.put(AppConstant.PRODUCT_ID, productId);
        ProductDetailAPI service = ServiceGenerator.createService(ProductDetailAPI.class);
        Call<BaseResponse<List<ProductCommentResponse>>> call = service.getProductComment(data);
        call.enqueue(new Callback<BaseResponse<List<ProductCommentResponse>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ProductCommentResponse>>> call, Response<BaseResponse<List<ProductCommentResponse>>> response) {
                int code = response.body().getCode();
                if (code == ResponseCode.OK.code){
                    listener.onSuccess(response.body().getData());
                }
                else{
                    listener.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<ProductCommentResponse>>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void likeProductRemote(String token, String productId, final ResponseListener<LikeResponseData> listener) {
        Map<String, String> data = new HashMap<>();
        data.put(AppConstant.TOKEN, token);
        data.put(AppConstant.PRODUCT_ID, productId);
        ProductDetailAPI service = ServiceGenerator.createService(ProductDetailAPI.class);
        Call<BaseResponse<LikeResponseData>> call = service.likeProduct(data);
        call.enqueue(new Callback<BaseResponse<LikeResponseData>>() {

            @Override
            public void onResponse(Call<BaseResponse<LikeResponseData>> call, Response<BaseResponse<LikeResponseData>> response) {
                BaseResponse<LikeResponseData> bodyResponse = response.body();

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
            public void onFailure(Call<BaseResponse<LikeResponseData>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void addProductCommentRemote(String token, String productId, String comment, String index, final ResponseListener<List<ProductCommentResponse>> listener) {
        Map<String, String> data = new HashMap<>();
        data.put(AppConstant.TOKEN, token);
        data.put(AppConstant.PRODUCT_ID, productId);
        data.put(AppConstant.COMMENT, comment);
        data.put(AppConstant.INDEX, index);
        ProductDetailAPI service = ServiceGenerator.createService(ProductDetailAPI.class);
        Call<BaseResponse<List<ProductCommentResponse>>> call = service.setProductComment(data);
        call.enqueue(new Callback<BaseResponse<List<ProductCommentResponse>>>() {

            @Override
            public void onResponse(Call<BaseResponse<List<ProductCommentResponse>>> call, Response<BaseResponse<List<ProductCommentResponse>>> response) {
                BaseResponse<List<ProductCommentResponse>> bodyResponse = response.body();

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
            public void onFailure(Call<BaseResponse<List<ProductCommentResponse>>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

}
