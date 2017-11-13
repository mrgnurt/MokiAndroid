package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.CampaignAPI;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.GetListCampaignResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trung on 02/11/2017.
 */

public class CampaignServiceImpl implements CampaignService {

    @Override
    public void getListCampaign(final ResponseListener<List<GetListCampaignResponseData>> listener) {
        CampaignAPI service = ServiceGenerator.createService(CampaignAPI.class);
        Call<BaseResponse<List<GetListCampaignResponseData>>> call =  service.callGetListCampaign();
        call.enqueue(new Callback<BaseResponse<List<GetListCampaignResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<GetListCampaignResponseData>>> call, Response<BaseResponse<List<GetListCampaignResponseData>>> response) {
                int code = response.body().getCode();
                if (code == ResponseCode.OK.code){
//                    listener.onSuccess(response.body().getData());
                }
                else{
//                    listener.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<GetListCampaignResponseData>>> call, Throwable t) {
//                listener.onFailure(t.getMessage());
            }
        });
    }
}
