package com.coho.moki.service;

import com.coho.moki.data.remote.ShipFromResponseData;
import com.coho.moki.data.remote.UserFollowResponseData;
import com.coho.moki.data.remote.UserInfoResponseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyenthanhtung on 15/11/2017.
 */

public interface UserService {
    void getUserInfo(String token, String userId, ResponseListener<UserInfoResponseData> listener);

    void getUserFollow(String token, String userId, Integer index, Integer count, String type, ResponseListener<ArrayList<UserFollowResponseData>> listener);

    void getUserShipFrom(int level, int parentId, ResponseListener<List<ShipFromResponseData>> listener);
}