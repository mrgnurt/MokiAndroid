package com.coho.moki.ui.user;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListProductAdapter;
import com.coho.moki.callback.OnClickProductItemListenner;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Brand;
import com.coho.moki.data.model.Category;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.model.User;
import com.coho.moki.data.model.UserProduct;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.data.remote.UserProductResponseData;
import com.coho.moki.service.ProductService;
import com.coho.moki.service.ProductServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.fragment.ListProduct.ListProductContract;
import com.coho.moki.ui.fragment.ListProduct.ListProductFragment;
import com.coho.moki.ui.fragment.ListProduct.ListProductPresenter;
import com.coho.moki.ui.fragment.ProductPager.ProductPagerFragment;
import com.coho.moki.ui.product.ProductDetailActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.SpaceItem;
import com.coho.moki.util.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/16/2017.
 */

public class UserProductFragment extends BaseFragment {

    private Integer index = 0;
    public static String userId;
    public UserProductFragment() {}

    public UserProductFragment(String id) {
        userId = id;
    }
    @BindView(R.id.rv_product_list)
    RecyclerView mRVProductList;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    ProductPagerFragment mProductPagerFragment;

    ListProductAdapter mListProductAdapter;

    ProductService mProductService = new ProductServiceImpl();

    public static ListProductFragment newInstance(String id){
        userId = id;
        ListProductFragment listProductFragment = new ListProductFragment();

        return listProductFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.refresh_gridview;
    }

    @Override
    protected void initView() {
        super.initView();
        initRV();
        initRefreshLayout();
        initProducts();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void initRV(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRVProductList.setLayoutManager(gridLayoutManager);

        int space = 13;
        SpaceItem spaceItem = new SpaceItem(space);
        mRVProductList.addItemDecoration(spaceItem);

        mListProductAdapter = new ListProductAdapter(new ArrayList<Product>());
        mRVProductList.setAdapter(mListProductAdapter);

        mListProductAdapter.addListener(new OnClickProductItemListenner() {
            @Override
            public void onClick(String productId) {
                Intent intent = new Intent(BaseApp.getContext(), ProductDetailActivity.class);
                intent.putExtra(AppConstant.PRODUCT_ID, productId);
                DialogUtil.showProgress(getActivity());
                startActivity(intent);
            }
        });
    }


    public void initRefreshLayout(){
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(2000);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                callGetLoadMoreProducts();
            }
        });
    }
    public void initProducts(){
        if (Utils.checkInternetAvailable()){
            DialogUtil.showProgress(getActivity());
            callGetProducts();
        }
        else {
            DialogUtil.showPopupError(getActivity(), BaseApp.getContext().getString(R.string.error_msg_internet_not_connect));
        }

    }

    public void showProducts(List<Product> products){
        mListProductAdapter.insertLastItem(products);
    }

    public void invisibleLoadMore(){
        mRefreshLayout.finishLoadmore();
    }

    public void invisibleRefresh(){
        mRefreshLayout.finishRefresh();
    }

    public void callGetProducts() {
        convertDataResponsetoProducts(new ArrayList<UserProductResponseData>());
        String token = AccountUntil.getUserToken();
        Integer count = AppConstant.COUNT_PRODUCTS_GET;
        String keyword = "";
        String categoryId = "";
        mProductService.getProductOfUser(token, index, count, userId, keyword, categoryId,
                new ResponseListener<ArrayList<UserProductResponseData>>() {

                    @Override
                    public void onSuccess(ArrayList<UserProductResponseData> dataResponse) {
                        DialogUtil.hideProgress();
                        convertDataResponsetoProducts(dataResponse);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        DialogUtil.hideProgress();
                        Utils.toastShort(BaseApp.getContext(), errorMessage);
                    }
                });
    }

    public void convertDataResponsetoProducts(ArrayList<UserProductResponseData> userProductResponseDatas){

        List<Product> products = new ArrayList<Product>();

        for (UserProductResponseData userProductResponseData:
                userProductResponseDatas) {


//            List<ImageResponseData> imageResponseDatas = productSmallResponceData.getImage();
//            List<Image> images = new ArrayList<>();
//            for (ImageResponseData imageResponseData: imageResponseDatas){
//                Image image = new Image(imageResponseData.getUrl());
//                images.add(image);
//            }

            Product product = new Product(
                    userProductResponseData.getId(),
                    userProductResponseData.getName(),
                    userProductResponseData.getImage(),
                    userProductResponseData.getPrice(),
                    userProductResponseData.getPricePercent(),
                    userProductResponseData.getIsLiked(),
                    userProductResponseData.getBanned(),
                    userProductResponseData.getDescribed(),
                    userProductResponseData.getLike(),
                    userProductResponseData.getComment());
            products.add(product);

        }
        showProducts(products);
    }

    public void callGetLoadMoreProducts() {
        String token = AccountUntil.getUserToken();
        final Integer count = AppConstant.COUNT_PRODUCTS_GET;
        String keyword = "";
        String categoryId = "";
        mProductService.getProductOfUser(token, index, count, userId, keyword, categoryId,
                new ResponseListener<ArrayList<UserProductResponseData>>() {

                    @Override
                    public void onSuccess(ArrayList<UserProductResponseData> dataResponse) {
                        convertDataResponsetoProducts(dataResponse);
                        index += count;
                        invisibleLoadMore();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Utils.toastShort(BaseApp.getContext(), errorMessage);
                    }
                });
    }
}
