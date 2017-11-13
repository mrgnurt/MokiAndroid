package com.coho.moki.ui.main_search;

import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.SearchProductResponseData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.service.SearchService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by trung on 03/11/2017.
 */

public class MainSearchPresenter implements MainSearchContract.Presenter {

    MainSearchContract.View mView;

    @Inject
    public MainSearchPresenter(){}

    @Override
    public void onAttach(MainSearchContract.View view) {
        mView = view;
    }
}
