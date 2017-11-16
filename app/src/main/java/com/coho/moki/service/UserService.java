package com.coho.moki.service;

import com.coho.moki.data.remote.UserInfoResponseData;

/**
 * Created by nguyenthanhtung on 15/11/2017.
 */

public interface UserService {
    void getUserInfo(String token, String userId, ResponseListener<UserInfoResponseData> listener);
}
