package com.coho.moki.ui.product_search;

import com.coho.moki.data.model.Product;
import com.coho.moki.ui.base.BasePresenter;
import com.coho.moki.ui.main_search.MainSearchContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 07/11/2017.
 */

public interface ProductSearchContract {

    interface View {
        void showProducts(List<Product> products);

        void showLoadProgress();

        void hideLoadProgress();

        void showPopup(String message);

        void setSearchHeader(String text);
    }

    interface Presenter extends BasePresenter<ProductSearchContract.View> {
        void callSearchProduct();

        void initProducts();

        void initParamSearch(String keyword, String sizeId, String brandId);

        ArrayList<Product> getProduts();


    }
}
