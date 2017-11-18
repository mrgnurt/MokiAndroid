package com.coho.moki.ui.fragment.NewsPager;

import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListMyLikeAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.service.ProductService;
import com.coho.moki.service.ProductServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.product.ProductCommentActivity;
import com.coho.moki.ui.product.ProductDetailActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by trung on 18/11/2017.
 */

public class MyLikeFragment extends BaseFragment {

    List<MyLikeResponseData> myLikeResponseDataList;
    ListMyLikeAdapter adapter;

    ProductService service = new ProductServiceImpl();

    @BindView(R.id.listView)
    ListView mListView;

    @BindView(R.id.emptyLayout)
    RelativeLayout mEmptyLayout;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    int indexCall = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.refresh_list2;
    }

    @Override
    protected void initView() {
        myLikeResponseDataList = new ArrayList<MyLikeResponseData>();

//        myLikeResponseDataList.add(new MyLikeResponseData("", "trung", 10000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT"));
//        myLikeResponseDataList.add(new MyLikeResponseData("", "trung", 10000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT"));
//        myLikeResponseDataList.add(new MyLikeResponseData("", "trung", 10000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT"));
//        myLikeResponseDataList.add(new MyLikeResponseData("", "trung", 10000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT"));
        adapter = new ListMyLikeAdapter(BaseApp.getContext(), myLikeResponseDataList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BaseApp.getContext(), ProductDetailActivity.class);
                intent.putExtra(AppConstant.PRODUCT_ID, myLikeResponseDataList.get(i).getId());
                DialogUtil.showProgress(getActivity());
                startActivity(intent);
            }
        });

        initRefreshLayout();

    }

    @Override
    protected void initData() {

        callPullRefresh();
    }

    public void initRefreshLayout(){
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                indexCall = 0;
                callPullRefresh();
            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                callLoadMore();
            }
        });

    }

    public void callLoadMore(){
        if (AccountUntil.getUserToken() != null){
            service.getMyLikeProduct(AccountUntil.getUserToken(), indexCall, 10, new ResponseListener<List<MyLikeResponseData>>() {
                @Override
                public void onSuccess(List<MyLikeResponseData> dataResponse) {
                    smartRefreshLayout.finishLoadmore();
                    indexCall++;

                    myLikeResponseDataList.addAll(dataResponse);
//                        Log.d("trungbrand", myLikeResponseDataList.size() + "");
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String errorMessage) {
                    smartRefreshLayout.finishLoadmore();
                    Utils.toastShort(BaseApp.getContext(), errorMessage);
                }
            });
        }
    }

    public void callPullRefresh(){
        if (AccountUntil.getUserToken() != null){
            service.getMyLikeProduct(AccountUntil.getUserToken(), indexCall, 10, new ResponseListener<List<MyLikeResponseData>>() {
                @Override
                public void onSuccess(List<MyLikeResponseData> dataResponse) {
                    smartRefreshLayout.finishRefresh();
                    indexCall++;

                    if (dataResponse.size() == 0){
                        mEmptyLayout.setVisibility(View.VISIBLE);
                        smartRefreshLayout.setVisibility(View.GONE);
                    }
                    else {
                        smartRefreshLayout.setVisibility(View.VISIBLE);
                        mEmptyLayout.setVisibility(View.GONE);
                        myLikeResponseDataList.clear();
                        myLikeResponseDataList.addAll(dataResponse);
//                        Log.d("trungbrand", myLikeResponseDataList.size() + "");
                        adapter.notifyDataSetChanged();

                    }

                }

                @Override
                public void onFailure(String errorMessage) {
                    smartRefreshLayout.finishRefresh();
                    Utils.toastShort(BaseApp.getContext(), errorMessage);
                }
            });
        }
    }
}
