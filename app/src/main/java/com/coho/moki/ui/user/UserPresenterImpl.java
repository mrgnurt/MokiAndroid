package com.coho.moki.ui.user;

import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.data.remote.UserInfoResponseData;
import com.coho.moki.service.ProductDetailService;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.service.UserService;
import com.coho.moki.ui.product.ProductDetailView;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;

import javax.inject.Inject;

/**
 * Created by nguyenthanhtung on 15/11/2017.
 */

public class UserPresenterImpl implements UserPresenter {
    private static final String LOG_TAG = "UserInfo";

    UserInfoView mUserInfoView;


    UserService mUserService;

    @Inject
    public UserPresenterImpl(UserService userService) {
        mUserService = userService;
    }

    @Override
    public void onAttach(UserInfoView view) {
        mUserInfoView = view;
    }

    @Override
    public void getUserInfoRemote(String token, String userId) {
        mUserService.getUserInfo(token, userId, new ResponseListener<UserInfoResponseData>() {
            @Override
            public void onSuccess(UserInfoResponseData dataResponse) {
                Log.d(LOG_TAG, dataResponse.toString());
                DialogUtil.hideProgress();
                mUserInfoView.setUserInfo(dataResponse);
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                Utils.toastShort(BaseApp.getContext(), errorMessage);
            }
        });
    }
}
