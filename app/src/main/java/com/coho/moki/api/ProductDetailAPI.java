package com.coho.moki.api;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.LikeResponseData;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Khanh Nguyen on 11/3/2017.
 */

public interface ProductDetailAPI {

    @POST("/products/detail")
    Call<BaseResponse<ProductDetailResponse>> getProductDetail(@Body Map<String, String> data);

    @POST("/products/comments")
    Call<BaseResponse<List<ProductCommentResponse>>> getProductComment(@Body Map<String, String> data);

    @POST("/products/like-product")
    Call<BaseResponse<LikeResponseData>> likeProduct(@Body Map<String, String> data);
}
