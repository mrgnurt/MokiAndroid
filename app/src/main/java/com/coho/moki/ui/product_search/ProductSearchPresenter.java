package com.coho.moki.ui.product_search;

import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.SearchProductResponseData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.service.SearchService;
import com.coho.moki.ui.main_search.MainSearchContract;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by trung on 07/11/2017.
 */

public class ProductSearchPresenter implements ProductSearchContract.Presenter {

    ProductSearchContract.View mView;
    SearchService mSearchService;
    int mIndex = 0;
    String mKeyword = null;
    String mSizeId = null;
    ArrayList<Product> mProducts;


    @Inject
    public ProductSearchPresenter(SearchService searchService){
        mSearchService = searchService;
    }

    @Override
    public void onAttach(ProductSearchContract.View view) {
        mView = view;
    }

    @Override
    public void callSearchProduct() {
        mSearchService.searchProduct(null, mKeyword, null, null, mSizeId, null, null, null, mIndex, AppConstant.COUNT_SEARCH_PRODUCT,
                new ResponseListener<List<SearchProductResponseData>>() {
                    @Override
                    public void onSuccess(List<SearchProductResponseData> dataResponse) {
                        Log.d("abc", "ks");
                        convertDataResponseToProduct(dataResponse);

                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Utils.toastShort(BaseApp.getContext(), errorMessage);
                    }
                });
    }

    @Override
    public void initProducts() {
        mProducts = new ArrayList<Product>();
    }

    @Override
    public void initParamSearch(String keyword, String sizeId) {
        mKeyword = keyword;
        mSizeId = sizeId;
    }

    @Override
    public ArrayList<Product> getProduts() {
        return mProducts;
    }

    private void convertDataResponseToProduct(List<SearchProductResponseData> dataResponse){

        ArrayList<Product> products = new ArrayList<Product>();

        for(SearchProductResponseData item : dataResponse){
            Product product = new Product(
                    item.getId(),
                    item.getName(),
                    null,
                    item.getPrice(),
                    item.getPricePercent(),
                    null,
                    null,
                    item.getLike(),
                    item.getComment());

            products.add(product);
        }

        mView.showProducts(products);
    }
}
