package com.coho.moki.ui.product;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductCategoryAdapter;
import com.coho.moki.data.remote.ProductCategoryResponse;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/14/2017.
 */

public class ProductCategoryActivity extends BaseActivity {

    @BindView(R.id.listView)
    PullAndLoadListView listView;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    List<ProductCategoryResponse> mCategoryList;
    ProductCategoryAdapter mCategoryAdapter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_product_category;
    }

    @Override
    public void initView() {
        btnNavRight.setVisibility(View.GONE);
        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

            public void onRefresh() {
                // Do work to refresh the list here.
                new ProductCategoryActivity.PullToRefreshDataTask().execute();
            }
        });
    }

    @Override
    public void initData() {
        txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.category)));
        txtHeader.setVisibility(View.VISIBLE);
        fakeData();
    }

    private void fakeData() {
        mCategoryList = new ArrayList<>();
        for (int i = 0; i < 15; ++i) {
            mCategoryList.add(null);
        }
        mCategoryAdapter = new ProductCategoryAdapter(this, R.layout.product_category_item, mCategoryList);
        listView.setAdapter(mCategoryAdapter);
    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonBack() {

    }

    private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

//            for (int i = 0; i < mAnimals.length; i++)
//                mListItems.addFirst(mAnimals[i]);
//            commentList.addFirst(fake());

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            mListItems.addFirst("Added after pull to refresh");

            // We need notify the adapter that the data have been changed
//            commentAdapter.notifyDataSetChanged();

            // Call onLoadMoreComplete when the LoadMore task, has finished
            listView.onRefreshComplete();

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            listView.onRefreshComplete();
        }
    }

}
