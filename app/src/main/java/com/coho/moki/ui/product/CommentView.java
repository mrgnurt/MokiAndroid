package com.coho.moki.ui.product;

import com.coho.moki.data.remote.ProductCommentResponse;

import java.util.List;

/**
 * Created by nguyenthanhtung on 13/11/2017.
 */

public interface CommentView {
    public void setProductComment(List<ProductCommentResponse> productCommentResponse);

}
