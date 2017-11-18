package com.coho.moki.service;

import com.coho.moki.data.remote.LikeResponseData;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;

import java.util.List;

/**
 * Created by Khanh Nguyen on 11/3/2017.
 */

public interface ProductDetailService {

    public void getProductDetailRemote(String token, String productId, final ResponseListener<ProductDetailResponse> listener);

    public void getProductCommentRemote(String productId, final ResponseListener<List<ProductCommentResponse>> listener);

    public void likeProductRemote(String token, String productId, final ResponseListener<LikeResponseData> listener);

    public void addProductCommentRemote(String token, String productId, String comment, String index, final ResponseListener<List<ProductCommentResponse>> listener);
}
