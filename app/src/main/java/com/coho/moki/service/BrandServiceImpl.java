package com.coho.moki.service;

import com.coho.moki.api.BrandAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.SearchProductResponseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.QueryMap;

/**
 * Created by trung on 07/11/2017.
 */

public class BrandServiceImpl implements BrandService {
    @Override
    public void getListBrand(String categoryId, final ResponseListener<List<BrandResponceData>> listener) {

        Map<String, Object> data = new HashMap<String, Object>();
        if (categoryId != null){
            data.put(AppConstant.CATEGORYID_TAG, categoryId);
        }

        BrandAPI service = ServiceGenerator.createService(BrandAPI.class);
        Call<BaseResponse<List<BrandResponceData>>> call =  service.callGetListBrand(data);
        call.enqueue(new Callback<BaseResponse<List<BrandResponceData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<BrandResponceData>>> call, Response<BaseResponse<List<BrandResponceData>>> response) {

                BaseResponse<List<BrandResponceData>> bodyResponse = response.body();

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
            public void onFailure(Call<BaseResponse<List<BrandResponceData>>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
