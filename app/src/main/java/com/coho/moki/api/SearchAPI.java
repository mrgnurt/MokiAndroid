package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.SearchProductResponseData;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by trung on 03/11/2017.
 */

public interface SearchAPI {
    @POST("/searches/products")
    Call<BaseResponse<List<SearchProductResponseData>>> callSearchProduct(@Body Map<String, Object> tag);
}
