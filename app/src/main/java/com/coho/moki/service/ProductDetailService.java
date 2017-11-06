package com.coho.moki.service;

import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;

import java.util.List;

/**
 * Created by Khanh Nguyen on 11/3/2017.
 */

public interface ProductDetailService {

    public void getProductDetailRemote(String token, String productId, final ResponseListener<ProductDetailResponse> listener);

    public void getProductCommentRemote(String productId, final ResponseListener<List<ProductCommentResponse>> listener);

}