package com.coho.moki.ui.fragment.ListProduct;

import android.util.Log;

import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Brand;
import com.coho.moki.data.model.Category;
import com.coho.moki.data.model.Image;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.ImageResponseData;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.service.ProductService;
import com.coho.moki.service.ProductServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.fragment.ProductPager.ProductPagerContract;
import com.coho.moki.ui.fragment.ProductPager.ProductPagerPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 23/10/2017.
 */

public class ListProductPresenter implements ListProductContract.Presenter {

    ListProductContract.View mView;
    ProductService mProductService;

    ArrayList<Product> mProducts;
    Category mCategory;
    String mLastId = "";

    public ListProductPresenter(){
        mProductService = new ProductServiceImpl();
    }

    @Override
    public void onAttach(ListProductContract.View view) {
        mView = view;
    }

    @Override
    public void callGetProducts() {
        mProductService.getListProduct("", mCategory.getCategoryId(), "", "", "0",
                AppConstant.COUNT_PRODUCTS_GET, new ResponseListener<GetListProductResponceData>() {
            @Override
            public void onSuccess(GetListProductResponceData dataResponse) {
                convertDataResponsetoProducts(dataResponse.getProducts());
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    @Override
    public void callGetLoadMoreProducts() {
        mProductService.getListProduct("", mCategory.getCategoryId(), "", mLastId, "0",
                AppConstant.COUNT_PRODUCTS_GET, new ResponseListener<GetListProductResponceData>() {
                    @Override
                    public void onSuccess(GetListProductResponceData dataResponse) {
                        convertDataResponsetoProducts(dataResponse.getProducts());
                        mLastId = dataResponse.getLastId();
                        mView.invisibleLoadMore();
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
    }


    public void convertDataResponsetoProducts(ArrayList<ProductSmallResponceData> productSmallResponceDatas){

        List<Product> products = new ArrayList<Product>();

        for (ProductSmallResponceData productSmallResponceData:
                productSmallResponceDatas) {

            List<BrandResponceData> brandResponceDatas = productSmallResponceData.getBrand();
            List<Brand> brands = new ArrayList<>();
            for (BrandResponceData brandResponceData: brandResponceDatas){
                Brand brand = new Brand(brandResponceData.getId(), brandResponceData.getName());
                brands.add(brand);
            }

//            List<ImageResponseData> imageResponseDatas = productSmallResponceData.getImage();
//            List<Image> images = new ArrayList<>();
//            for (ImageResponseData imageResponseData: imageResponseDatas){
//                Image image = new Image(imageResponseData.getUrl());
//                images.add(image);
//            }

            Product product = new Product(
                    productSmallResponceData.getId(),
                    productSmallResponceData.getName(),
                    productSmallResponceData.getImage(),
                    productSmallResponceData.getPrice(),
                    productSmallResponceData.getPricePercent(),
                    brands,
                    productSmallResponceData.getDescribed(),
                    productSmallResponceData.getLike(),
                    productSmallResponceData.getComment());
            products.add(product);

        }
        mView.showProducts(products);
    }

    public ArrayList<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(ArrayList<Product> mProducts) {
        this.mProducts = mProducts;
    }

    public void initProducts(){
        mProducts = new ArrayList<Product>();
    }

    @Override
    public void setCategory(Category category) {
        mCategory = category;
    }
}