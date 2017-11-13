package com.coho.moki.ui.fragment.ListProduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ViewFlipper;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListProductAdapter;
import com.coho.moki.callback.OnClickProductItemListenner;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Brand;
import com.coho.moki.data.model.Category;
import com.coho.moki.data.model.Image;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.ImageResponseData;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.fragment.ProductPager.ProductPagerFragment;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.ui.product.ProductDetailActivity;
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
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;

/**
 * Created by trung on 14/10/2017.
 */

public class ListProductFragment extends BaseFragment implements ListProductContract.View {

    @BindView(R.id.rv_product_list)
    RecyclerView mRVProductList;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.view_flipper)
    ViewFlipper mViewFlipper;

    ProductPagerFragment mProductPagerFragment;

    ListProductAdapter mListProductAdapter;

    ListProductContract.Presenter mPresenter;

    public static ListProductFragment newInstance(Category category){
        ListProductFragment listProductFragment = new ListProductFragment();

        Bundle args = new Bundle();
        args.putParcelable(AppConstant.CATEGORY_TAG, category);
        listProductFragment.setArguments(args);

        return listProductFragment;
    }

    @Override
    protected void handleArguments(Bundle arguments) {
        Category category = arguments.getParcelable(AppConstant.CATEGORY_TAG);
        mPresenter = new ListProductPresenter();
        mPresenter.onAttach(this);
        mPresenter.setCategory(category);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.refresh_gridview;
    }

    @Override
    protected void initView() {
        super.initView();
//        mViewFlipper.setDisplayedChild();
        initRV();
        initRefreshLayout();
        getProducts();
        mProductPagerFragment= (ProductPagerFragment) getFragmentManager().findFragmentByTag("home");
        mRVProductList.addOnScrollListener(onScrollListener);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void initRV(){

        mPresenter.initProducts();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRVProductList.setLayoutManager(gridLayoutManager);

        int space = 13;
        SpaceItem spaceItem = new SpaceItem(space);
        mRVProductList.addItemDecoration(spaceItem);

        mListProductAdapter = new ListProductAdapter(mPresenter.getProducts());
        mRVProductList.setAdapter(mListProductAdapter);

        mListProductAdapter.addListener(new OnClickProductItemListenner() {
            @Override
            public void onClick(String productId) {
                Intent intent = new Intent(BaseApp.getContext(), ProductDetailActivity.class);
                intent.putExtra(AppConstant.PRODUCT_ID, productId);
                startActivity(intent);
            }
        });
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener(){
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//                Log.d("trung", "dx" + dx);
//            Log.d("trung", "dy " + dy);

            if (dy > 15){
                mProductPagerFragment.setVisibleButtonCamera(false);
            }
            else if (dy < -15){
                mProductPagerFragment.setVisibleButtonCamera(true);
            }

            int offsetScroll = recyclerView.computeVerticalScrollOffset();
            Log.d("trung", "offset " + offsetScroll);
            if (offsetScroll < 3){
                mProductPagerFragment.setVisibleScrollableLayout(true);
            }
            else{
                mProductPagerFragment.setVisibleScrollableLayout(false);
            }
        }
    };

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

                mPresenter.callGetLoadMoreProducts();
            }
        });
    }

    public void getProducts(){
        if (Utils.checkInternetAvailable()){
            DialogUtil.showProgress(getActivity());
            mPresenter.callGetProducts();
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

    public void changeViewMode() {
//        refreshView(false);
        AnimationFactory.flipTransition(this.mViewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
    }

}
