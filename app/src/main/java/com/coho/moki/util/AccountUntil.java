package com.coho.moki.util;

import android.os.AsyncTask;

import com.coho.moki.data.model.User;

/**
 * Created by trung on 27/09/2017.
 */

public class AccountUntil {

    public static String getAccountId(){
        return SharedPrefUtils.getString(AppConstant.MY_ID, null);
    }

    public static String getUsername() {
        return SharedPrefUtils.getString(AppConstant.MY_USERNAME, null);
    }

    public static String getAvatarUrl() {
        return SharedPrefUtils.getString(AppConstant.MY_AVATAR_URL, null);
    }

    public synchronized static void saveInfoAccount(final User user) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SharedPrefUtils.putString(AppConstant.MY_ID, user.getUserId());
                SharedPrefUtils.putString(AppConstant.MY_USERNAME, user.getUsername());
                SharedPrefUtils.putString(AppConstant.MY_AVATAR_URL, user.getAvatarUrl());
                return null;
            }
        }.execute();
    }

    public synchronized static void removeInfoAccount() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SharedPrefUtils.removeKey(AppConstant.MY_ID);
                SharedPrefUtils.removeKey(AppConstant.MY_USERNAME);
                SharedPrefUtils.removeKey(AppConstant.MY_AVATAR_URL);
                return null;
            }
        }.execute();

    }
}
