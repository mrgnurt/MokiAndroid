package com.coho.moki.ui.product;

import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.data.remote.LikeResponseData;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;
import com.coho.moki.service.ProductDetailService;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.google.gson.annotations.Until;

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
                mProductDetailView.setData(dataResponse);
                DialogUtil.hideProgress();
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
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
                DialogUtil.hideProgress();
                mProductDetailView.setProductComment(dataResponse);
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                Log.d(LOG_TAG, errorMessage);
            }
        });
    }

    @Override
    public void likeProductRemote(String token, String productId) {
        mProductDetailService.likeProductRemote(token, productId, new ResponseListener<LikeResponseData>() {
            @Override
            public void onSuccess(LikeResponseData dataResponse) {
                DialogUtil.hideProgress();
                mProductDetailView.setLikeProduct(dataResponse);
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                Utils.toastShort(BaseApp.getContext(), errorMessage);
            }
        });
    }

}
