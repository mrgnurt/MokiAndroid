package com.coho.moki.ui.product;

import android.util.Log;

import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.service.ProductDetailService;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.util.DialogUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by nguyenthanhtung on 13/11/2017.
 */

public class CommentPresenterImpl implements CommentPresenter {

    private static final String LOG_TAG = "CommentPresenterImpl";
    CommentView mCommentView;

    ProductDetailService mProductDetailService;

    @Inject
    public CommentPresenterImpl(ProductDetailService productDetailService) {
        mProductDetailService = productDetailService;
    }

    @Override
    public void onAttach(CommentView view) {
        mCommentView = view;
    }

    @Override
    public void addProductCommentRemote(String token, String productId, String comment, String index) {
        mProductDetailService.addProductCommentRemote(token, productId, comment, index, new ResponseListener<List<ProductCommentResponse>>() {
            @Override
            public void onSuccess(List<ProductCommentResponse> dataResponse) {
                DialogUtil.hideProgress();
                mCommentView.setProductComment(dataResponse);
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
                mCommentView.setProductComment(dataResponse);
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                Log.d(LOG_TAG, errorMessage);
            }
        });
    }
}
