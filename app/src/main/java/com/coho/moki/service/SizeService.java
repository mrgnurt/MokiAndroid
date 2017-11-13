package com.coho.moki.service;

import com.coho.moki.data.remote.GetListCampaignResponseData;
import com.coho.moki.data.remote.SizeResponseData;

import java.util.List;

/**
 * Created by trung on 07/11/2017.
 */

public interface SizeService {
    void getListSize(String categoryId, ResponseListener<List<SizeResponseData>> listener);
}
