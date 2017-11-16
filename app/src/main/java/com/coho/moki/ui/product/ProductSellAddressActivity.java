package com.coho.moki.ui.product;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/16/2017.
 */

public class ProductSellAddressActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.txtFinish)
    TextView txtFinish;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @Override
    public int setContentViewId() {
        return R.layout.address_choose;
    }

    @Override
    public void initView() {
        btnNavRight.setVisibility(View.INVISIBLE);
        // TODO: set text for txtHeader

    }

    @Override
    public void initData() {

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
}
