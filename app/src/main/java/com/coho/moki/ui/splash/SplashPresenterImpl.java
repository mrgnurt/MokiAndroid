package com.coho.moki.ui.splash;

import com.coho.moki.util.AccountUntil;

import javax.inject.Inject;

import dagger.Module;

/**
 * Created by trung on 22/09/2017.
 */
public class SplashPresenterImpl implements SplashPresenter {

    SplashView mSplashView;

    @Inject
    public SplashPresenterImpl(){
    }

    @Override
    public void checkUserLogin() {
        String userId = AccountUntil.getAccountId();

        if (userId == null){
            mSplashView.openLoginActivity();
        }
        else{
            mSplashView.openMainActivity();
        }
    }

    @Override
    public void onAttach(SplashView view) {
        mSplashView = view;
    }
}
