package com.coho.moki.ui.fragment.ListProduct;

import com.coho.moki.adapter.customadapter.ListProductAdapter;
import com.coho.moki.data.model.Category;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 23/10/2017.
 */

public interface ListProductContract {

    public interface View{

        void showProducts(List<Product> products);

        void showProductsTimeLine(List<ProductSmallResponceData> products);

        void getProducts();

        void invisibleLoadMore();

        void invisibleRefresh();
    }

    public interface Presenter extends BasePresenter<View>{

        void initProducts();

        ArrayList<Product> getProducts();

        void setProducts(ArrayList<Product> mProducts);

        void callGetProducts();

        void callGetLoadMoreProducts();

        void setCategory(Category category);
    }
}
