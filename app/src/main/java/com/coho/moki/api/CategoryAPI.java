package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.ProductCategoryResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nguyenthanhtung on 17/11/2017.
 */

public interface CategoryAPI {
    @POST("/categories")
    Call<BaseResponse<List<ProductCategoryResponse>>> getCategoryList(@Body Map<String, String> tag);
}
