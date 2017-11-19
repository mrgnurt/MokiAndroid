package com.coho.moki.ui.product_search;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListProductAdapter;
import com.coho.moki.callback.OnClickProductItemListenner;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Product;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.main_search.MainSearchContract;
import com.coho.moki.ui.product.ProductDetailActivity;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.SpaceItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by trung on 07/11/2017.
 */

public class ProductSearchActivity extends BaseActivity implements ProductSearchContract.View {

    @Inject
    ProductSearchContract.Presenter mPresenter;

    ListProductAdapter mListProductAdapter;

    @BindView(R.id.txtHeader)
    TextView mTxtHeader;

    @BindView(R.id.rv_product_list)
    RecyclerView mRVProductList;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.search_no_result)
    RelativeLayout mSearchNoResult;

    @OnClick(R.id.txtAdvandedSearch)
    public void clickTxtAdvandedSearch(){
        this.finish();
    }

    @OnClick(R.id.btnNavLeft)
    public void clickButtonBack(){
        this.finish();
    }

    @Override
    public int setContentViewId() {
        return R.layout.view_product_list;
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        BaseApp.getActivityComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Override
    public void initView() {
        initRV();
        initRefreshLayout();
    }

    @Override
    public void initData() {
        callSearchProduct();
    }

    public void initRV(){

        mPresenter.initProducts();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseApp.getContext(), 2);
        mRVProductList.setLayoutManager(gridLayoutManager);

        int space = 13;
        SpaceItem spaceItem = new SpaceItem(space);
        mRVProductList.addItemDecoration(spaceItem);

        mListProductAdapter = new ListProductAdapter(mPresenter.getProduts());
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

                refreshlayout.finishRefresh(2000);
            }
        });
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        String keyword = intent.getStringExtra(AppConstant.KEYWORD_TAG);
        String sizeId = intent.getStringExtra(AppConstant.PRODUCT_SIZE_ID_TAG);
        String brandId = intent.getStringExtra(AppConstant.PRODUCT_BRAND_ID_TAG);
        String header = intent.getStringExtra(AppConstant.INFO_SEARCH_TAG);

        Log.d("trungsearch", header);
        mTxtHeader.setText(header);

        mPresenter.initParamSearch(keyword, sizeId, brandId);

    }

    private void callSearchProduct(){
        mPresenter.callSearchProduct();
    }

    @Override
    public void showProducts(List<Product> products) {
        mListProductAdapter.insertLastItem(products);
    }

    public void setSearchHeader(String text) {
        mTxtHeader.setText(text);
    }

    @Override
    public void showSearchNotFound() {
        mRefreshLayout.setVisibility(View.GONE);
        mSearchNoResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadProgress() {
        DialogUtil.showProgress(this);
    }

    @Override
    public void hideLoadProgress() {
        DialogUtil.hideProgress();
    }

    @Override
    public void showPopup(String message) {
        DialogUtil.showPopup(this, message);
    }

}
