package com.coho.moki.ui.main_search;

/**
 * Created by trung on 03/11/2017.
 */

public class MainSearchPresenter implements MainSearchContract.Presenter {

    MainSearchContract.View mView;

    @Override
    public void onAttach(MainSearchContract.View view) {
        mView = view;
    }
}
