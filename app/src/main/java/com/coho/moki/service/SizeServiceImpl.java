package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.BrandAPI;
import com.coho.moki.api.SizeAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.SizeResponseData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trung on 07/11/2017.
 */

public class SizeServiceImpl implements SizeService {

    @Inject
    public SizeServiceImpl(){
    }

    @Override
    public void getListSize(String categoryId, final ResponseListener<List<SizeResponseData>> listener) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (categoryId != null){
            data.put(AppConstant.CATEGORYID_TAG, categoryId);
        }

        SizeAPI service = ServiceGenerator.createService(SizeAPI.class);
        Call<BaseResponse<List<SizeResponseData>>> call =  service.callGetListSize(data);
        call.enqueue(new Callback<BaseResponse<List<SizeResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<SizeResponseData>>> call, Response<BaseResponse<List<SizeResponseData>>> response) {
                int code = response.body().getCode();
                if (code == ResponseCode.OK.code){
                    Log.d("size", response.body().getData().size() + "");
                    if (response.body().getData().get(0) == null){
                        Log.d("size", "djsj");
                    }

                    listener.onSuccess(response.body().getData());
                }
                else{
                    Log.d("size", response.body().getMessage());
//                    listener.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<SizeResponseData>>> call, Throwable t) {
                Log.d("size", t.getMessage());
//                listener.onFailure(t.getMessage());
            }
        });
    }
}
