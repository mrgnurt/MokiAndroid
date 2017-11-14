package com.coho.moki.ui.product;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;
import com.kyleduo.switchbutton.SwitchButton;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/8/2017.
 */

public class AddProductActivity extends BaseActivity {

    private final String TAG = "AddProductActivity";

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

    @BindView(R.id.edtDescription)
    EditText edtDescription;

    @BindView(R.id.topContainer)
    LinearLayout topContainer;

    @BindView(R.id.switch_product_type)
    SwitchButton switchProductType;

    @BindView(R.id.layout_fast_sell)
    LinearLayout layoutFastSell;

    @BindView(R.id.switch_auto_accept)
    SwitchButton switchAutoAccept;

    @BindView(R.id.layout_bargain)
    LinearLayout layoutBargain;

    @BindView(R.id.switch_allow_offer)
    SwitchButton switchAllowOffer;

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

    @BindView(R.id.titleAddress)
    TextView titleAddress;

    @BindView(R.id.edtBuyPlace)
    EditText edtBuyPlace;

    @BindView(R.id.bottomContainer)
    LinearLayout bottomContainer;

    @BindView(R.id.edtBuyPrice)
    EditText edtBuyPrice;

    @BindView(R.id.productPriceContainer)
    LinearLayout productPriceContainer;

    @BindView(R.id.txtTotal)
    TextView txtTotal;

    @BindView(R.id.txtServiceArticle)
    TextView txtServiceArticle;

    @BindView(R.id.btnAddProduct)
    Button btnAddProduct;

    @BindView(R.id.topKeyboardLayout)
    RelativeLayout topKeyboardLayout;

    @BindView(R.id.preBtn)
    ImageButton btnPrev;

    @BindView(R.id.preBtnContainer)
    RelativeLayout preBtnContainer;

    @BindView(R.id.nextBtn)
    ImageButton btnNext;

    @BindView(R.id.doneBtn)
    TextView btnDone;

    private int imgPos;
    private List<Uri> uriList = new ArrayList<>();
    private static final int REQUEST_CAMERA = 1001;
//    private List<ImageView> imgViewList;

    @Override
    public int setContentViewId() {
        return R.layout.add_product;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(AppConstant.ADD_PRODUCT_IMG);
        imgPos = intent.getIntExtra(AppConstant.ADD_PRODUCT_IMG_POS, 0);
        uriList.add(uri);
        Log.d(TAG, "uri = " + uri.getPath());
        Log.d(TAG, "imgPos = " + imgPos);
        img1.setImageURI(uri);
        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.VISIBLE);
        initHeader();
        initListenerChooseImage();
    }

    private void initListenerChooseImage() {
        // get data from camera activity
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.img1:

                        break;
                    case R.id.img2:
                        openCamera(2);
                        break;
                    case R.id.img3:
                        openCamera(3);
                        break;
                    case R.id.img4:
                        openCamera(4);
                        break;
                }
            }
        };
        img1.setOnClickListener(listener);
        img2.setOnClickListener(listener);
        img3.setOnClickListener(listener);
        img4.setOnClickListener(listener);

    }

    private void initHeader() {
        txtHeaderRunning.setVisibility(View.GONE);
        txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.add_product)));
        btnBack.setVisibility(View.VISIBLE);
        btnNavRight.setVisibility(View.VISIBLE);
        btnNavRight.setScaleType(ImageView.ScaleType.FIT_XY);
        btnNavRight.setImageResource(R.drawable.btn_delete);
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonNavLeft() {
        onBackPressed();
    }

    @OnClick(R.id.btnNavRight)
    public void onClickButtonDelete() {

    }

    private void openCamera(int imgPos) {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(AppConstant.ADD_PRODUCT_IMG_POS, imgPos);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Intent intent;
            Uri uri;
            switch (requestCode) {
                case REQUEST_CAMERA:
                    intent = getIntent();
                    imgPos = intent.getIntExtra(AppConstant.ADD_PRODUCT_IMG_POS, 0);
                    uri = intent.getParcelableExtra(AppConstant.ADD_PRODUCT_IMG);
                    uriList.add(uri);
                    int len = uriList.size();
                    Log.d(TAG, "uri lis size = " + len);
                    if (len == 2) {
                        img2.setImageURI(uri);
                        img3.setVisibility(View.VISIBLE);
                    } else if (len == 3) {
                        img3.setImageURI(uri);
                        img4.setVisibility(View.VISIBLE);
                    } else if (len == 4) {
                        img4.setImageURI(uri);
                    }
                    break;
            }
        }
    }
}
