package com.coho.moki.ui.product;

import com.coho.moki.data.model.ProductComment;
import com.coho.moki.data.remote.LikeResponseData;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;

import java.util.List;

/**
 * Created by Khanh Nguyen on 10/12/2017.
 */

public interface ProductDetailView {

    public void setData(ProductDetailResponse productDetailResponse);

    public void setProductComment(List<ProductCommentResponse> productCommentResponse);

    public void setLikeProduct(LikeResponseData likeResponseData);
}
