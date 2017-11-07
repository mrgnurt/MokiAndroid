package com.coho.moki.ui.product;

import android.util.Log;

import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;
import com.coho.moki.service.ProductDetailService;
import com.coho.moki.service.ResponseListener;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Khanh Nguyen on 10/12/2017.
 */

public class ProductDetailPresenterImpl implements ProductDetailPresenter {

    private static final String LOG_TAG = "PdDetailPresenterImpl";

    ProductDetailView mProductDetailView;

    ProductDetailService mProductDetailService;

    @Inject
    public ProductDetailPresenterImpl(ProductDetailService productDetailService) {
        mProductDetailService = productDetailService;
    }

    @Override
    public void onAttach(ProductDetailView view) {
        mProductDetailView = view;
    }


    @Override
    public void getProductDetailRemote(String token, String productId) {
        Log.d(LOG_TAG, "ProductDetailPresenterImpl");
        mProductDetailService.getProductDetailRemote(token, productId, new ResponseListener<ProductDetailResponse>() {
            @Override
            public void onSuccess(ProductDetailResponse dataResponse) {
                Log.d(LOG_TAG, dataResponse.toString());
                mProductDetailView.fetchData(dataResponse);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.d(LOG_TAG, errorMessage);
            }
        });
    }

    @Override
    public void getProductCommentRemote(String productId) {
        mProductDetailService.getProductCommentRemote(productId, new ResponseListener<List<ProductCommentResponse>>() {
            @Override
            public void onSuccess(List<ProductCommentResponse> dataResponse) {
                Log.d(LOG_TAG, dataResponse.toString());
                mProductDetailView.setProductComment(dataResponse);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.d(LOG_TAG, errorMessage);
            }
        });
    }
}
