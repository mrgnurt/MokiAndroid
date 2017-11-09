package com.coho.moki.ui.product;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/8/2017.
 */

public class AddProductActivity extends BaseActivity {

    @BindView(R.id.img1)
    ImageView img1;

    @BindView(R.id.imgPlayVideoIcon)
    ImageView imgPlayVideoIcon;

    @BindView(R.id.img2)
    ImageView img2;

    @BindView(R.id.img3)
    ImageView img3;

    @BindView(R.id.img4)
    ImageView img4;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtHeaderRunning)
    TextView txtHeaderRunning;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtComment)
    EditText edtComment;

    @BindView(R.id.topContainer)
    LinearLayout topContainer;

    @BindView(R.id.layout_fast_sell)
    LinearLayout layoutFastSell;

    @BindView(R.id.switch_auto_accept)
    SwitchButton switchAutoAccept;

    @BindView(R.id.layout_bargain)
    LinearLayout layoutBargain;

    @BindView(R.id.switch_allow_offer)
    SwitchButton switch_allow_offer;

    @BindView(R.id.edtCategory)
    EditText edtCategory;

    @BindView(R.id.edtStatus)
    EditText edtStatus;

    @BindView(R.id.llBrand)
    LinearLayout llBrand;

    @BindView(R.id.edtBrand)
    EditText edtBrand;

    @BindView(R.id.llSize)
    LinearLayout llSize;

    @BindView(R.id.edtSize)
    EditText edtSize;

    @BindView(R.id.llWeight)
    LinearLayout llWeight;

    @BindView(R.id.edtWeight)
    EditText edtWeight;

    @BindView(R.id.llDimension)
    LinearLayout llDimension;

    @BindView(R.id.edtDimension)
    EditText edtDimension;

    @Override
    public int setContentViewId() {
        return R.layout.add_product;
    }

    @Override
    public void initView() {
        txtHeaderRunning.setVisibility(View.INVISIBLE);
        txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.add_product)));
        btnBack.setVisibility(View.VISIBLE);
        btnNavRight.setVisibility(View.VISIBLE);
        btnNavRight.setImageResource(R.drawable.delete);
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonNavLeft() {
        onBackPressed();
    }

    

}
