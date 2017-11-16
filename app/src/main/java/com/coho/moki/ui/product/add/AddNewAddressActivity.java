package com.coho.moki.ui.product.add;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/16/2017.
 */

public class AddNewAddressActivity extends BaseActivity {

    private static final String TAG = "AddNewAddressActivity";
    private static final int REQUEST_CITY = 1;
    private static final int REQUEST_DISTRICT = 2;
    private static final int REQUEST_WARD = 3;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.edtCity)
    EditText edtCity;

    @BindView(R.id.edtDistrict)
    EditText edtDistrict;

    @BindView(R.id.edtWard)
    EditText edtWard;

    @BindView(R.id.edtHamlet)
    EditText edtHamlet;

    @BindView(R.id.cbDefault)
    CheckBox cbDefault;

    @BindView(R.id.txtFinish)
    TextView txtFinish;

    @Override
    public int setContentViewId() {
        return R.layout.new_address_choose;
    }

    @Override
    public void initView() {
        btnNavRight.setVisibility(View.INVISIBLE);
        txtHeader.setText(Utils.toTitleCase(Utils.getResourceString(R.string.new_address)));
        initClickListener();
    }

    @Override
    public void initData() {

    }

    private void initClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.edtCity:
                        // TODO: startActivityForResult
                        Intent intent = new Intent(BaseApp.getContext(), SearchAddressActivity.class);
                        startActivityForResult(intent, REQUEST_CITY);
                        break;
                    case R.id.edtDistrict:

                        break;
                    case R.id.edtWard:

                        break;
                    case R.id.txtFinish:
                        // TODO: call API to save new address
                        break;
                }
            }
        };
        edtCity.setOnClickListener(listener);
        edtDistrict.setOnClickListener(listener);
        edtWard.setOnClickListener(listener);
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
