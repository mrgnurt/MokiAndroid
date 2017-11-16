package com.coho.moki.ui.product.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/15/2017.
 */

public class ProductStatusActivity extends BaseActivity {

    private static final String TAG = "ProductStatusActivity";

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.layout_all_state)
    RelativeLayout layoutAllState;

    @BindView(R.id.layout_state_new)
    RelativeLayout layoutStateNew;

    @BindView(R.id.layout_state_like_new)
    RelativeLayout layoutStateLikeNew;

    @BindView(R.id.layout_state_good)
    RelativeLayout layoutStateGood;

    @BindView(R.id.layout_state_goodish)
    RelativeLayout layoutStateGoodish;

    @BindView(R.id.layout_state_old)
    RelativeLayout layoutStateOld;

    @Override
    public int setContentViewId() {
        return R.layout.activity_product_status;
    }

    @Override
    public void initView() {
        txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.status)));
        layoutAllState.setVisibility(View.GONE);
        btnNavRight.setVisibility(View.INVISIBLE);
        initClickListener();
    }

    @Override
    public void initData() {

    }

    private void initClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click on relative layout");
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                switch (v.getId()) {
                    case R.id.layout_state_new:
                        bundle.putInt(AppConstant.PRODUCT_STATUS_KEY, 1);
                        bundle.putString(AppConstant.PRODUCT_STATUS_VALUE, getResources().getString(R.string.new_));
                        break;
                    case R.id.layout_state_like_new:
                        bundle.putInt(AppConstant.PRODUCT_STATUS_KEY, 2);
                        bundle.putString(AppConstant.PRODUCT_STATUS_VALUE, getResources().getString(R.string.like_new));
                        break;
                    case R.id.layout_state_good:
                        bundle.putInt(AppConstant.PRODUCT_STATUS_KEY, 3);
                        bundle.putString(AppConstant.PRODUCT_STATUS_VALUE, getResources().getString(R.string.good));
                        break;
                    case R.id.layout_state_goodish:
                        bundle.putInt(AppConstant.PRODUCT_STATUS_KEY, 4);
                        bundle.putString(AppConstant.PRODUCT_STATUS_VALUE, getResources().getString(R.string.goodish));
                        break;
                    case R.id.layout_state_old:
                        bundle.putInt(AppConstant.PRODUCT_STATUS_KEY, 5);
                        bundle.putString(AppConstant.PRODUCT_STATUS_VALUE, getResources().getString(R.string.old));
                        break;
                }
                intent.putExtra(AppConstant.PRODUCT_STATUS, bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        };
        layoutStateNew.setOnClickListener(listener);
        layoutStateLikeNew.setOnClickListener(listener);
        layoutStateGood.setOnClickListener(listener);
        layoutStateGoodish.setOnClickListener(listener);
        layoutStateOld.setOnClickListener(listener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonBack() {
        onBackPressed();
    }

}
