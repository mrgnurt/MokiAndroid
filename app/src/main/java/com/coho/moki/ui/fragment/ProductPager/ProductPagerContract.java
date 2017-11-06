package com.coho.moki.ui.fragment.ProductPager;

import com.coho.moki.ui.base.BasePresenter;

/**
 * Created by trung on 02/11/2017.
 */

public interface ProductPagerContract {

    public interface View {

        void showBannerSlider();

        void setVisibleScrollableLayout(boolean visible);

        void setVisibleButtonCamera(boolean visible);
    }

    public interface Presenter extends BasePresenter<ProductPagerContract.View> {

        void callServiceGetCampaigns();
    }
}
