package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.GetListCampaignResponseData;
import com.coho.moki.data.remote.GetListProductResponceData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by trung on 02/11/2017.
 */

public interface CampaignAPI {
    @POST("/campaigns")
    Call<BaseResponse<GetListCampaignResponseData>> callGetListCampaign();
}
