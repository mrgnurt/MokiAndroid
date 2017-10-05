package com.coho.moki.service;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.LoginResponseData;

/**
 * Created by trung on 06/10/2017.
 */

public interface LoginService {

    void login(String phoneNumber, String password, final ResponseListener<LoginResponseData> listener);
}
