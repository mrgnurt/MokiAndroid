package com.coho.moki.ui.main;

import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.service.ProductService;
import com.coho.moki.service.ResponseListener;

import java.util.ArrayList;

/**
 * Created by trung on 17/10/2017.
 */

public class MainPresenterImpl implements MainPresenter {

    MainView mMainView;

    @Override
    public void onAttach(MainView view) {
        mMainView = view;
    }
}
