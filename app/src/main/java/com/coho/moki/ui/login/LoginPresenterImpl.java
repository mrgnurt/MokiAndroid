package com.coho.moki.ui.login;

import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.data.model.User;
import com.coho.moki.data.remote.LoginResponseData;
import com.coho.moki.service.LoginService;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.Utils;

import javax.inject.Inject;

/**
 * Created by trung on 04/10/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {

    LoginView mLoginView;
    LoginService mLoginService;

    @Inject
    public LoginPresenterImpl(LoginService loginService){
        mLoginService = loginService;
    }

    @Override
    public void requestLoginRemote(String phoneNumber, String password) {
        mLoginService.login(phoneNumber, password, new ResponseListener<LoginResponseData>() {
            @Override
            public void onSuccess(LoginResponseData response) {

                User user = new User(response.getId(), response.getUsername(), response.getToken(), response.getAvatar());
                AccountUntil.saveInfoAccount(user);
                mLoginView.openMainActivity();
            }

            @Override
            public void onFailure(String errorMessage) {
                Utils.toastShort(BaseApp.getContext(), errorMessage);
            }
        });
    }

    @Override
    public void onAttach(LoginView view) {
        mLoginView = view;
    }
}
