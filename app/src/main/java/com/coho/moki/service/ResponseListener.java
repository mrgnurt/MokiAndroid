package com.coho.moki.service;

/**
 * Created by trung on 05/10/2017.
 */

public interface ResponseListener<S> {

    void onSuccess(S dataResponse);

    void onFailure(String errorMessage);
}
