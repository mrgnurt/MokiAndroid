package com.coho.moki.ui.product;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductCategoryAdapter;
import com.coho.moki.data.remote.ProductCategoryResponse;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;
import com.costum.android.widget.PullToRefreshListView;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/14/2017.
 */

public class ProductCategoryActivity extends BaseActivity {

    @BindView(R.id.listView)
    PullToRefreshListView listView;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    LinkedList<ProductCategoryResponse> mCategoryList;
    ProductCategoryAdapter mCategoryAdapter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_product_category;
    }

    @Override
    public void initView() {
        btnNavRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.category)));
        txtHeader.setVisibility(View.VISIBLE);
        fakeData();
    }

    private void fakeData() {
        mCategoryList = new LinkedList<>();
        mCategoryList.add(null);
        mCategoryList.add(null);
        mCategoryList.add(null);
        mCategoryAdapter = new ProductCategoryAdapter(this, R.layout.product_category_item, mCategoryList);
        listView.setAdapter(mCategoryAdapter);
    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonBack() {

    }

}
