package com.coho.moki.ui.main_search;

import com.coho.moki.ui.base.BasePresenter;

/**
 * Created by trung on 03/11/2017.
 */

public interface MainSearchContract {
    interface View {
        void openProductSearchActivity(String keyword, String sizeId);
    }

    interface Presenter extends BasePresenter<MainSearchContract.View> {
    }
}
