package com.coho.moki.service;

import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.GetListCampaignResponseData;

import java.util.List;

/**
 * Created by trung on 07/11/2017.
 */

public interface BrandService {
    void getListBrand(String categoryId, ResponseListener<List<BrandResponceData>> listener);
}
