package com.coho.moki.service;

import com.coho.moki.data.remote.GetListCampaignResponseData;

import java.util.List;

/**
 * Created by trung on 02/11/2017.
 */

public interface CampaignService {
    void getListCampaign(ResponseListener<List<GetListCampaignResponseData>> listener);
}
