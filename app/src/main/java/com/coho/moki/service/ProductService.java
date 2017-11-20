package com.coho.moki.service;

import com.coho.moki.data.remote.CheckNewItemResponse;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.data.remote.UserProductResponseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 17/10/2017.
 */

public interface ProductService {

    void getListProduct(String token, String categoryId, String campaignId, String lastId,
                        String index, int count, final ResponseListener<GetListProductResponceData> listener);

    void getProductOfUser(String token, int index, int count, String userId, String keyword, String categoryId,
                          final ResponseListener<ArrayList<UserProductResponseData>> listener);

    public void getMyLikeProduct(String token, int index, int count, final ResponseListener<List<MyLikeResponseData>> listener);

    void checkNewItem(String lastId, String categoryId, final ResponseListener<CheckNewItemResponse> listener);
}
