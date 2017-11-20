package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.CheckNewItemResponse;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.data.remote.UserProductResponseData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @POST("/products/user-listing")
    Call<BaseResponse<ArrayList<UserProductResponseData>>> callGetUserProduct(@Body Map<String, Object> tag);

    @POST("/products/my-like")
    Call<BaseResponse<List<MyLikeResponseData>>> callGetMyLikeProduct(@Body Map<String, Object> tag);

    @POST("/products/new-item")
    Call<BaseResponse<CheckNewItemResponse>> callCheckNewItem(@Body Map<String, String> tag);
}
