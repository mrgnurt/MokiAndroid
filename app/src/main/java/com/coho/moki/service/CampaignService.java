package com.coho.moki.service;

import com.coho.moki.data.remote.GetListCampaignResponseData;

/**
 * Created by trung on 02/11/2017.
 */

public interface CampaignService {
    void getListCampaign(ResponseListener<GetListCampaignResponseData> listener);
}
