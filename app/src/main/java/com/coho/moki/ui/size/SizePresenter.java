package com.coho.moki.ui.size;

import com.coho.moki.BaseApp;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.model.Size;
import com.coho.moki.data.remote.SizeResponseData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.service.SizeService;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by trung on 07/11/2017.
 */

public class SizePresenter implements SizeContract.Presenter {

    SizeContract.View mView;
    SizeService mSizeService;
    ArrayList<Size> mSizes;

    @Override
    public void onAttach(SizeContract.View view) {
        mView = view;
    }

    @Inject
    public SizePresenter(SizeService sizeService){
        mSizeService = sizeService;
    }

    @Override
    public void callSearchProduct(String categoryId) {
        mSizeService.getListSize(categoryId, new ResponseListener<List<SizeResponseData>>() {
            @Override
            public void onSuccess(List<SizeResponseData> dataResponse) {
                convertDataResponseToProduct(dataResponse);
            }

            @Override
            public void onFailure(String errorMessage) {
                Utils.toastShort(BaseApp.getContext(), errorMessage);
            }
        });
    }

    @Override
    public void initSizes() {
        mSizes = new ArrayList<Size>();
    }

    @Override
    public ArrayList<Size> getSizes() {
        return mSizes;
    }

    private void convertDataResponseToProduct(List<SizeResponseData> dataResponse){


        for(SizeResponseData item : dataResponse){
            Size size = new Size(item.getId(), item.getSizeName());
            mSizes.add(size);
        }

        mView.showSizes(mSizes);
    }
}
