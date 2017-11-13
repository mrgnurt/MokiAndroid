package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.GetListCampaignResponseData;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by trung on 07/11/2017.
 */

public interface BrandAPI {
    @POST("/brands")
    Call<BaseResponse<List<BrandResponceData>>> callGetListBrand(@Body Map<String, Object> tag);
}
