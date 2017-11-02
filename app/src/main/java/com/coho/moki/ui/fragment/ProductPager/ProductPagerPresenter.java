package com.coho.moki.ui.fragment.ProductPager;


import android.util.Log;

import com.coho.moki.data.remote.GetListCampaignResponseData;
import com.coho.moki.service.CampaignService;
import com.coho.moki.service.CampaignServiceImpl;
import com.coho.moki.service.ResponseListener;

/**
 * Created by trung on 02/11/2017.
 */

public class ProductPagerPresenter implements ProductPagerContract.Presenter {

    ProductPagerContract.View mView;
    CampaignService mCampaignService;

    public ProductPagerPresenter(){
        mCampaignService = new CampaignServiceImpl();
    }

    @Override
    public void onAttach(ProductPagerContract.View view) {
        Log.d("trung", "vao day");
        mView = view;
    }

    @Override
    public void callServiceGetCampaigns() {
        mCampaignService.getListCampaign(new ResponseListener<GetListCampaignResponseData>() {
            @Override
            public void onSuccess(GetListCampaignResponseData dataResponse) {

            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
}
