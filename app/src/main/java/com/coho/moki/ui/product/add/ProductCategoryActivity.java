package com.coho.moki.ui.product.add;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductCategoryAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.ProductCategoryResponse;
import com.coho.moki.data.remote.ProductCategoryResponse;
import com.coho.moki.service.CategoryService;
import com.coho.moki.service.CategoryServiceImpl;
import com.coho.moki.service.ResponseListener;
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
    ListView listView;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    List<ProductCategoryResponse> mCategoryList;
    ProductCategoryAdapter mCategoryAdapter;
    String categoryName;
    CategoryService categoryService;

    @Override
    public int setContentViewId() {
        return R.layout.activity_product_category;
    }

    @Override
    public void initView() {
        btnNavRight.setVisibility(View.GONE);
        listView.setOnItemClickListener(new OnClickItemListCategory());
        categoryService = new CategoryServiceImpl();
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
            getCategoryList(response.getId());
        } else { // main category
            txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.category)));
            getCategoryList("");
        }
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
            ProductCategoryResponse response = mCategoryList.get(position);
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

    public void getCategoryList(final String categoryId) {
        categoryService.getCategoryList(categoryId, new ResponseListener<List<ProductCategoryResponse>>() {
            @Override
            public void onSuccess(List<ProductCategoryResponse> dataResponse) {
                if (categoryId == "") {
                    mCategoryList = dataResponse.subList(1, dataResponse.size() - 1);
                } else {
                    mCategoryList.addAll(dataResponse);
                }
                mCategoryAdapter = new ProductCategoryAdapter(ProductCategoryActivity.this, R.layout.product_category_item, mCategoryList);
                listView.setAdapter(mCategoryAdapter);
            }

            @Override
            public void onFailure(String errorMessage) {
                Utils.toastShort(ProductCategoryActivity.this, errorMessage);
            }
        });
    }

}
