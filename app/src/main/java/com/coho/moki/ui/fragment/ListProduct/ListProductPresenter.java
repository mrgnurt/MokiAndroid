package com.coho.moki.ui.fragment.ListProduct;

import android.util.Log;
import android.view.View;

import com.coho.moki.BaseApp;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Brand;
import com.coho.moki.data.model.Category;
import com.coho.moki.data.model.Image;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.ImageResponseData;
import com.coho.moki.data.remote.LikeResponseData;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.service.ProductDetailService;
import com.coho.moki.service.ProductDetailServiceImpl;
import com.coho.moki.service.ProductService;
import com.coho.moki.service.ProductServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.fragment.ProductPager.ProductPagerContract;
import com.coho.moki.ui.fragment.ProductPager.ProductPagerPresenter;
import com.coho.moki.util.APICacheUtils;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 23/10/2017.
 */

public class ListProductPresenter implements ListProductContract.Presenter {

    ListProductContract.View mView;
    ProductService mProductService;
    ProductDetailService mProductDetailService;

    ArrayList<Product> mProducts;
    Category mCategory;
    String mLastId = "";
    int mIndex = 0;

    public ListProductPresenter(){
        mProductService = new ProductServiceImpl();
        mProductDetailService = new ProductDetailServiceImpl();
    }

    @Override
    public void onAttach(ListProductContract.View view) {
        mView = view;
    }

    @Override
    public void callGetProducts() {
        convertDataResponsetoProducts(new ArrayList<ProductSmallResponceData>());
        if (Utils.checkInternetAvailable()) {
            getProductFromRemote();
        } else {
            getProductFromLocal();
        }
    }

    public void getProductFromRemote() {
        mProductService.getListProduct("", mCategory.getCategoryId(), "", "", mIndex + "",
                AppConstant.COUNT_PRODUCTS_GET, new ResponseListener<GetListProductResponceData>() {
                    @Override
                    public void onSuccess(GetListProductResponceData dataResponse) {
                        DialogUtil.hideProgress();
                        ArrayList<ProductSmallResponceData> products = dataResponse.getProducts();
                        if (products != null && products.size() > 0) {
                            mIndex = products.size();
                            showProducts(products);
                            APICacheUtils.get().saveResult(products, mCategory.getCategoryId());
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        DialogUtil.hideProgress();
                        Utils.toastShort(BaseApp.getContext(), errorMessage);
                    }
                });
    }

    public void getProductFromLocal() {
        DialogUtil.hideProgress();
        ArrayList<ProductSmallResponceData> products =
                APICacheUtils
                        .get()
                        .getProducts(mCategory.getCategoryId(), ProductSmallResponceData.getType());
        if (products != null && products.size() > 0) {
            mIndex = products.size();
            showProducts(products);
        }
    }

    public void showProducts(ArrayList<ProductSmallResponceData> products) {
        convertDataResponsetoProducts(products);
        mView.showProductsTimeLine(products);
    }

    @Override
    public void callGetLoadMoreProducts() {
        mProductService.getListProduct("", mCategory.getCategoryId(), "", mLastId, mIndex + "",
                AppConstant.COUNT_PRODUCTS_GET, new ResponseListener<GetListProductResponceData>() {
                    @Override
                    public void onSuccess(GetListProductResponceData dataResponse) {
                        convertDataResponsetoProducts(dataResponse.getProducts());
                        mView.showProductsTimeLine(dataResponse.getProducts());
                        mLastId = dataResponse.getLastId();
                        mView.invisibleLoadMore();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Utils.toastShort(BaseApp.getContext(), errorMessage);
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

    @Override
    public void likeProductRemote(String token, String productId) {
        mProductDetailService.likeProductRemote(token, productId, new ResponseListener<LikeResponseData>() {
            @Override
            public void onSuccess(LikeResponseData dataResponse) {
                mView.setViewLikeTimeLine(dataResponse.getLike());
                DialogUtil.hideProgress();
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                Utils.toastShort(BaseApp.getContext(), errorMessage);
            }
        });
    }
}
