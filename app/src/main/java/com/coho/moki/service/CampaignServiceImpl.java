package com.coho.moki.service;

import android.util.Log;

import com.coho.moki.api.CampaignAPI;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.GetListCampaignResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trung on 02/11/2017.
 */

public class CampaignServiceImpl implements CampaignService {

    @Override
    public void getListCampaign(ResponseListener<GetListCampaignResponseData> listener) {
        CampaignAPI service = ServiceGenerator.createService(CampaignAPI.class);
        Call<BaseResponse<GetListCampaignResponseData>> call =  service.callGetListCampaign();
        call.enqueue(new Callback<BaseResponse<GetListCampaignResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<GetListCampaignResponseData>> call, Response<BaseResponse<GetListCampaignResponseData>> response) {
                int code = response.body().getCode();
                if (code == ResponseCode.OK.code){
                    Log.d("trung", response.body().getMessage());
//                    listener.onSuccess(response.body().getData());
                }
                else{
                    Log.d("trung", response.body().getMessage());
//                    listener.onFailure(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<GetListCampaignResponseData>> call, Throwable t) {

            }
        });
    }
}
