package com.coho.moki.ui.product.add;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/17/2017.
 */

public class SearchAddressActivity extends BaseActivity {

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @Override
    public int setContentViewId() {
        return R.layout.search_stall;
    }

    @Override
    public void initView() {
        btnNavRight.setVisibility(View.INVISIBLE);
        txtHeader.setText(Utils.toTitleCase(Utils.getResourceString(R.string.address)));
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
