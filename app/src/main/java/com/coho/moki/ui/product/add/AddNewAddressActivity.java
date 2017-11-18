package com.coho.moki.ui.product.add;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Size;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.ship_from.ShipFromActivity;
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

    //day la id cua cac city, district, ward
    private int parentIdCity = -1;
    private int parentIdDistrict = -1;
    private int parentIdWARD = -1;

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
                        Intent intent = new Intent(BaseApp.getContext(), ShipFromActivity.class);
                        intent.putExtra(AppConstant.LEVEL_TAG, REQUEST_CITY);
                        intent.putExtra(AppConstant.PARENT_ID_TAG, -1);
                        startActivityForResult(intent, REQUEST_CITY);
                        break;
                    case R.id.edtDistrict:
                        Intent intent1 = new Intent(BaseApp.getContext(), ShipFromActivity.class);
                        intent1.putExtra(AppConstant.LEVEL_TAG, REQUEST_DISTRICT);
//                        Utils.toastShort(AddNewAddressActivity.this, parentIdCity + "");
                        intent1.putExtra(AppConstant.PARENT_ID_TAG, parentIdCity);
                        startActivityForResult(intent1, REQUEST_DISTRICT);
                        break;
                    case R.id.edtWard:
                        Intent intent2 = new Intent(BaseApp.getContext(), ShipFromActivity.class);
                        intent2.putExtra(AppConstant.LEVEL_TAG, REQUEST_WARD);
//                        Utils.toastShort(AddNewAddressActivity.this, parentIdCity + "");
                        intent2.putExtra(AppConstant.PARENT_ID_TAG, parentIdDistrict);
                        startActivityForResult(intent2, REQUEST_WARD);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CITY){
            if (resultCode == AppConstant.RESULT_CODE_SHIPFORM){
                parentIdCity = data.getIntExtra(AppConstant.SHIPFROM_TAG_ID, -1);
                Utils.toastShort(AddNewAddressActivity.this, parentIdCity + "");
                edtCity.setText(data.getStringExtra(AppConstant.SHIPFROM_TAG_NAME));
            }
        }
        else if (requestCode == REQUEST_DISTRICT){
            if (resultCode == AppConstant.RESULT_CODE_SHIPFORM){
                parentIdDistrict = data.getIntExtra(AppConstant.SHIPFROM_TAG_ID, -1);
                edtDistrict.setText(data.getStringExtra(AppConstant.SHIPFROM_TAG_NAME));
            }
        }
        else if (requestCode == REQUEST_WARD){
            if (resultCode == AppConstant.RESULT_CODE_SHIPFORM){
                parentIdWARD = data.getIntExtra(AppConstant.SHIPFROM_TAG_ID, -1);
                edtWard.setText(data.getStringExtra(AppConstant.SHIPFROM_TAG_NAME));
            }
        }
    }

}
