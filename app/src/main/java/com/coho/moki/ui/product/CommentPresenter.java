package com.coho.moki.ui.product;

import com.coho.moki.ui.base.BasePresenter;

/**
 * Created by nguyenthanhtung on 13/11/2017.
 */

public interface CommentPresenter extends BasePresenter<CommentView> {

    void addProductCommentRemote(String token, String productId, String comment, String index);


    void getProductCommentRemote(String productId);
}
