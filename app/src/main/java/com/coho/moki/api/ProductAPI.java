package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.GetListProductResponceData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by trung on 17/10/2017.
 */

public interface ProductAPI {
    @POST("/products")
    Call<BaseResponse<GetListProductResponceData>> callGetListProduct(@Body Map<String, Object> tag);
}
