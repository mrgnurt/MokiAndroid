package com.coho.moki.service;

import com.coho.moki.api.CategoryAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ProductCategoryResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguyenthanhtung on 17/11/2017.
 */

public class CategoryServiceImpl implements CategoryService{
    @Override
    public void getCategoryList(String parentId, final ResponseListener<List<ProductCategoryResponse>> listener) {
        Map<String, String> data = new HashMap<>();
        data.put(AppConstant.PARENTID_TAG, parentId);
        
        CategoryAPI service = ServiceGenerator.createService(CategoryAPI.class);
        Call<BaseResponse<List<ProductCategoryResponse>>> call =  service.getCategoryList(data);
        call.enqueue(new Callback<BaseResponse<List<ProductCategoryResponse>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ProductCategoryResponse>>> call, Response<BaseResponse<List<ProductCategoryResponse>>> response) {
                BaseResponse<List<ProductCategoryResponse>> bodyResponse = response.body();

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
            public void onFailure(Call<BaseResponse<List<ProductCategoryResponse>>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
