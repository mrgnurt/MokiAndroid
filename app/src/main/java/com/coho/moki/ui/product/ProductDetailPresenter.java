package com.coho.moki.ui.product;

import com.coho.moki.data.remote.ProductDetailResponse;
import com.coho.moki.ui.base.BasePresenter;

/**
 * Created by Khanh Nguyen on 10/12/2017.
 */

public interface ProductDetailPresenter extends BasePresenter<ProductDetailView> {

    public void getProductDetailRemote(String token, String productId);

    public void getProductCommentRemote(String productId);

    public void likeProductRemote(String token, String productId);

}
