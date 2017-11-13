package com.coho.moki.ui.login;

import android.provider.Settings;
import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.User;
import com.coho.moki.data.remote.LoginResponseData;
import com.coho.moki.service.LoginService;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;

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

        String deviceId = AccountUntil.getDeviceId();

        if (deviceId == null){
            String time = Utils.stringForTime(new Date().getTime());

            deviceId = Settings.Secure.getString(BaseApp.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID) + time;
        }


        final String finalDeviceId = deviceId;
        mLoginService.login(phoneNumber, password, deviceId, new ResponseListener<LoginResponseData>() {
            @Override
            public void onSuccess(LoginResponseData response) {
                DialogUtil.hideProgress();
                User user = new User(response.getId(), response.getUsername(), response.getToken(), response.getAvatar());
                AccountUntil.saveInfoAccount(user);
                if (AccountUntil.getDeviceId() == null){
                    AccountUntil.saveDeviceId(finalDeviceId);
                }
                setDeviceToken();
                mLoginView.openMainActivity();
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                Utils.toastShort(BaseApp.getContext(), errorMessage);
            }
        });
    }

    @Override
    public void onAttach(LoginView view) {
        mLoginView = view;
    }

    private void setDeviceToken(){

        String deviceToken = FirebaseInstanceId.getInstance().getToken();

        if (deviceToken == null){
            deviceToken = FirebaseInstanceId.getInstance().getToken();
        }
        Log.d("token1234", AccountUntil.getUserToken());
        mLoginService.setDeviceToken(AccountUntil.getUserToken(), deviceToken, new ResponseListener<String>() {
            @Override
            public void onSuccess(String dataResponse) {

            }

            @Override
            public void onFailure(String errorMessage) {
                Utils.toastShort(BaseApp.getContext(), errorMessage);
            }
        });
    }
}
