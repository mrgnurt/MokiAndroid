package com.coho.moki.service;

import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.LoginResponseData;

/**
 * Created by trung on 06/10/2017.
 */

public interface LoginService {

    void login(String phoneNumber, String password, String deviceId, final ResponseListener<LoginResponseData> listener);

    void setDeviceToken(String token, String deviceToken, final ResponseListener<String> listener);
}
