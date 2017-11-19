package com.coho.moki.ui.product_search;

import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.SearchProductResponseData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.service.SearchService;
import com.coho.moki.ui.main_search.MainSearchContract;
import com.coho.moki.util.DialogUtil;
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
    String mBrandId = null;
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
        mView.showLoadProgress();
        mSearchService.searchProduct(null, mKeyword, null, mBrandId, mSizeId, null, null, null, mIndex, AppConstant.COUNT_SEARCH_PRODUCT,
                new ResponseListener<List<SearchProductResponseData>>() {
                    @Override
                    public void onSuccess(List<SearchProductResponseData> searchResults) {
                        Log.d("search", "successful");
                        mView.hideLoadProgress();
                        convertSearchResultsToProduct(searchResults);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.d("search", "fail");
                        mView.hideLoadProgress();

                        if (errorMessage.equals(AppConstant.SEARCH_NOT_FOUND)){
                            mView.showSearchNotFound();
                        }
                        else {
                            mView.showPopup(errorMessage);
                        }
//                        Utils.toastShort(BaseApp.getContext(), errorMessage);
                    }
                });
    }

    @Override
    public void initProducts() {
        mProducts = new ArrayList<Product>();
    }

    @Override
    public void initParamSearch(String keyword, String sizeId, String brandId) {
        mKeyword = keyword;
        mSizeId = sizeId;
        mBrandId = brandId;
    }

    @Override
    public ArrayList<Product> getProduts() {
        return mProducts;
    }

    private void convertSearchResultsToProduct(List<SearchProductResponseData> searchResults){

        ArrayList<Product> products = new ArrayList<Product>();
        for(SearchProductResponseData item : searchResults){
            List<String> productImages = new ArrayList<>();
            if (item.getImage() != null) {
                productImages.add(item.getImage());
            }

            Product product = new Product(
                    item.getId(),
                    item.getName(),
                    productImages,
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
