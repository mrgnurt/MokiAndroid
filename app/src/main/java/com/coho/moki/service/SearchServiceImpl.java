package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.SearchAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ConversationResponseData;
import com.coho.moki.data.remote.SearchProductResponseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trung on 06/11/2017.
 */

public class SearchServiceImpl implements SearchService {

    @Inject
    public SearchServiceImpl(){
    }

    @Override
    public void searchProduct(String token, String keyword, String categoryId, String brandId, String productSizeId, String priceMin, String priceMax, String condition, int index, int count, final ResponseListener<List<SearchProductResponseData>> listener) {
        final Map<String, Object> data = new HashMap<>();

        if (keyword != null){
            data.put(AppConstant.KEYWORD_TAG, keyword);
        }
        if (productSizeId != null){
            data.put(AppConstant.PRODUCT_SIZE_ID_TAG, productSizeId);
        }
        if (brandId != null){
            data.put(AppConstant.PRODUCT_BRAND_ID_TAG, brandId);
        }

        data.put(AppConstant.INDEX_TAG, index);
        data.put(AppConstant.COUNT_TAG, count);

        SearchAPI searchAPI = ServiceGenerator.createService(SearchAPI.class);
        Call<BaseResponse<List<SearchProductResponseData>>> call = searchAPI.callSearchProduct(data);
        call.enqueue(new Callback<BaseResponse<List<SearchProductResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<SearchProductResponseData>>> call, Response<BaseResponse<List<SearchProductResponseData>>> response) {
                BaseResponse<List<SearchProductResponseData>> bodyResponse = response.body();

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        listener.onFailure(AppConstant.NO_FETCH_DATA);
                    }
                    return;
                }

                if (bodyResponse == null) {
                    listener.onFailure(AppConstant.NO_FETCH_DATA);
                    return;
                }

                if (bodyResponse.getCode() != ResponseCode.OK.code) {
                    listener.onFailure(bodyResponse.getMessage());
                    return;
                }

                listener.onSuccess(bodyResponse.getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<List<SearchProductResponseData>>> call, Throwable t) {
                listener.onFailure(AppConstant.CALL_ERR);
                t.printStackTrace();
            }
        });
    }
}
