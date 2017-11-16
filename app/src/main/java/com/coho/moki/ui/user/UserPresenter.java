package com.coho.moki.ui.user;

import com.coho.moki.ui.base.BasePresenter;
import com.coho.moki.ui.product.ProductDetailView;

/**
 * Created by nguyenthanhtung on 15/11/2017.
 */

public interface UserPresenter extends BasePresenter<UserInfoView> {
    public void getUserInfoRemote(String token, String userId);
}
