package com.coho.moki.service;

/**
 * Created by tonquangtu on 18/11/2017.
 */

public interface LogoutService {

    void logout(String token, ResponseListener listener);
}
