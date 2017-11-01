package com.coho.moki.ui.fragment.ListProduct;

import android.util.Log;

import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.service.ProductService;
import com.coho.moki.service.ProductServiceImpl;
import com.coho.moki.service.ResponseListener;

/**
 * Created by trung on 23/10/2017.
 */

public class ListProductPresenter implements ListProductContract.Presenter {

    ListProductContract.View mView;
    ProductService mProductService;

    public ListProductPresenter(){
        mProductService = new ProductServiceImpl();
    }

    @Override
    public void onAttach(ListProductContract.View view) {
        mView = view;
    }

    @Override
    public void callGetProductsService(String token, String categoryId, String campaignId, String lastId, String index, int count, ResponseListener<GetListProductResponceData> listener) {
        mProductService.getListProduct(token, categoryId, campaignId, lastId, index, count, listener);
    }
}
