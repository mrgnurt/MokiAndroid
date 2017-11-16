package com.coho.moki.ui.product.add;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/16/2017.
 */

public class AddNewAddressActivity extends BaseActivity {

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @Override
    public int setContentViewId() {
        return R.layout.new_address_choose;
    }

    @Override
    public void initView() {
        btnNavRight.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData() {

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
