package com.coho.moki.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListProductAdapter;
import com.coho.moki.data.model.Product;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.util.SpaceItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by trung on 14/10/2017.
 */

public class ListProductFragment extends BaseFragment {

    @BindView(R.id.rv_product_list)
    RecyclerView mRVProductList;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

//    @BindView(R.id.layout_timeline)
//    StickyListHeadersListView mLayoutTimeline;

    @Override
    protected int getLayoutId() {
        return R.layout.refresh_gridview;
    }

    @Override
    protected void initView() {
        super.initView();
        initRV();
        initRefreshLayout();
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

        ArrayList<Product> products = new ArrayList<Product>();
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));
        products.add(new Product("", "Đồ ăn cho bé", "", "50 K", "", "", "", "100", "100"));

        ListProductAdapter adapter = new ListProductAdapter(products, this);
        mRVProductList.setAdapter(adapter);
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
                refreshlayout.finishLoadmore(2000);
            }
        });
    }
}
