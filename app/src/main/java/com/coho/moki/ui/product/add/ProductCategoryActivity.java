package com.coho.moki.ui.product.add;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductCategoryAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.ProductCategoryResponse;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/14/2017.
 */

public class ProductCategoryActivity extends BaseActivity {

    private final String TAG = "ProductCategoryActivity";
    private final int REQUEST_CHILD_CATEGORY = 111;

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
    String categoryName;

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
        listView.setOnItemClickListener(new OnClickItemListCategory());
    }

    @Override
    public void initData() {
        mCategoryList = new ArrayList<>();
        txtHeader.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        ProductCategoryResponse response = intent.getParcelableExtra(AppConstant.CATEGORY);
        if (response != null) { // response != null iff in subcategory
            categoryName = response.getName();
            txtHeader.setText(Utils.toTitleCase(categoryName));
            response.setHasChild(0);
            response.setName(getResources().getString(R.string.all)); // this is parent category
            mCategoryList.add(response);
            // TODO: call api to get list child category with categoryId
        } else { // main category
            txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.category)));
            // TODO: call api to get list category (if parentId = null => show in main category)
        }
        // TODO: remove fakeData() when call api
        fakeData();
    }

    private void fakeData() {
        for (int i = 0; i < 15; ++i) {
            ProductCategoryResponse response = new ProductCategoryResponse();
            response.setName("Category name " + i);
            if (i % 2 == 0) {
                response.setHasChild(1); // has child
            } else {
                response.setHasChild(0); // has not child
            }
            mCategoryList.add(response);
        }
        mCategoryAdapter = new ProductCategoryAdapter(this, R.layout.product_category_item, mCategoryList);
        listView.setAdapter(mCategoryAdapter);
    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHILD_CATEGORY:
                    setResult(Activity.RESULT_OK, data);
                    finish();
                    break;
            }
        }
    }

    private class OnClickItemListCategory implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // TODO: Handling when click "Tất cả" in child category

            Log.d(TAG, "click on item: " + position);
            ProductCategoryResponse response = mCategoryList.get(position - 1);
            if (response != null) {
                Integer hasChild = response.getHasChild();
                if (hasChild != null) {
                    Intent intent;
                    switch (hasChild) {
                        case 0:
                            intent = new Intent();
                            if (response.getName().equals(getResources().getString(R.string.all))) {
                                response.setName(categoryName);
                            }
                            intent.putExtra(AppConstant.CATEGORY, response);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                            break;
                        case 1:
                            intent = new Intent(BaseApp.getContext(), ProductCategoryActivity.class);
                            intent.putExtra(AppConstant.CATEGORY, response);
                            startActivityForResult(intent, REQUEST_CHILD_CATEGORY);
                            break;
                    }
                }
            }
        }

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
