package com.coho.moki.service;

import com.coho.moki.data.remote.GetListProductResponceData;

/**
 * Created by trung on 17/10/2017.
 */

public interface ProductService {

    void getListProduct(String token, String categoryId, String campaignId, String lastId,
                        String index, int count, final ResponseListener<GetListProductResponceData> listener);
}
