package com.coho.moki.ui.login;

import com.coho.moki.ui.base.BasePresenter;

/**
 * Created by trung on 30/09/2017.
 */

public interface LoginPresenter extends BasePresenter<LoginView>{

    void requestLoginRemote(String phoneNumber, String password);
}
