package com.coho.moki.ui.product.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.ProductCategoryResponse;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/8/2017.
 */

public class AddProductActivity extends BaseActivity {

    // TODO: add event when click on edtSize, edtBrand, edtWeight
    // TODO: fix edtPrice on text change

    private final String TAG = "AddProductActivity";
    private static final int REQUEST_PRODUCT_STATUS = 1;
    private static final int REQUEST_PRODUCT_CATEGORY = 2;
    private static final int REQUEST_SELL_ADDRESS = 3;
    private static final int REQUEST_CAMERA = 1001;

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

    @BindView(R.id.edtSellAddress)
    EditText edtSellAddress;

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

    private int editFocusPosition;

    private int imgPos;
    private List<Uri> uriList = new ArrayList<>();

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
//        img1.setImageURI(uri);
        img1.setImageBitmap(Utils.getThumbnailImage(uri));
        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.VISIBLE);
        initHeader();
        initListenerChooseImage();
        initFocusListenerForEditText();
        initClickListener();
        initTextChangeListener();
        switchAutoAccept.setChecked(true);
        topKeyboardLayout.setVisibility(View.GONE);
    }

    private void initListenerChooseImage() {
        // get data from camera activity
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.img1:
                        if (uriList.size() >= 1) {
                            openDialog(0);
                        } else {
                            openCamera(0);
                        }
                        break;
                    case R.id.img2:
                        if (uriList.size() >= 2) {
                            openDialog(1);
                        } else {
                            openCamera(1);
                        }
                        break;
                    case R.id.img3:
                        if (uriList.size() >= 3) {
                            openDialog(2);
                        } else {
                            openCamera(2);
                        }
                        break;
                    case R.id.img4:
                        Log.d(TAG, "img4 click, list size - " + uriList.size());
                        if (uriList.size() == 4) {
                            openDialog(3);
                        } else {
                            openCamera(3);
                        }
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

    private void openDialog(final int imgPos) {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
//        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product_image);
        Button btnEdit = dialog.findViewById(R.id.btnEdit);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        ImageView imgGesture = dialog.findViewById(R.id.imgGesture);
        imgGesture.setImageURI(uriList.get(imgPos));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(imgPos);
                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriList.remove(imgPos);
                updateUriListAfterRemove();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateUriListAfterRemove() {
        int currentSize = uriList.size();
        switch (currentSize) {
            case 0:
                img1.setImageResource(R.drawable.img_camera_trigger);
                img2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                img1.setImageBitmap(Utils.getThumbnailImage(uriList.get(0)));
                img2.setImageResource(R.drawable.img_camera_trigger);
                img3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                img1.setImageBitmap(Utils.getThumbnailImage(uriList.get(0)));
                img2.setImageBitmap(Utils.getThumbnailImage(uriList.get(1)));
                img3.setImageResource(R.drawable.img_camera_trigger);
                img4.setVisibility(View.INVISIBLE);
                break;
            case 3:
                img1.setImageBitmap(Utils.getThumbnailImage(uriList.get(0)));
                img2.setImageBitmap(Utils.getThumbnailImage(uriList.get(1)));
                img3.setImageBitmap(Utils.getThumbnailImage(uriList.get(2)));
                img4.setImageResource(R.drawable.img_camera_trigger);
                break;
        }
    }

    private void openAlertDialog(final int imgPos) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.dialog_product_image, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setView(view);
        Button btnEdit = dialog.findViewById(R.id.btnEdit);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        ImageView imgGesture = dialog.findViewById(R.id.imgGesture);
        imgGesture.setImageURI(uriList.get(imgPos));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    setResultFromCamera(data);
                    break;
                case REQUEST_PRODUCT_STATUS:
                    setResultFromProductStatus(data);
                    break;
                case REQUEST_PRODUCT_CATEGORY:
                    // TODO: set result from ProductCategoryActivity
                    setResultFromProductCategory(data);
                    break;
                case REQUEST_SELL_ADDRESS:
                    // TODO: set result from ProductSellAddressActivity
                    setResultFromProductSellAddress(data);
                    break;
            }
        }
    }

    private void setResultFromCamera(Intent data) {
        imgPos = data.getIntExtra(AppConstant.ADD_PRODUCT_IMG_POS, 0);
        Uri uri = data.getParcelableExtra(AppConstant.ADD_PRODUCT_IMG);
        int currentSize = uriList.size();
        if (currentSize >= imgPos + 1) {
            uriList.set(imgPos, uri);
        } else {
            uriList.add(uri);
            currentSize = uriList.size();
        }
        switch (imgPos) {
            case 0:
                img1.setImageBitmap(Utils.getThumbnailImage(uri));
                if (currentSize == imgPos + 1) {
                    img2.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                img2.setImageBitmap(Utils.getThumbnailImage(uri));
                if (currentSize == imgPos + 1) {
                    img3.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                img3.setImageBitmap(Utils.getThumbnailImage(uri));
                if (currentSize == imgPos + 1) {
                    img4.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                img4.setImageBitmap(Utils.getThumbnailImage(uri));
                break;
        }
    }

    private void setResultFromProductStatus(Intent data) {
        Bundle bundle = data.getBundleExtra(AppConstant.PRODUCT_STATUS);
        String status = bundle.getString(AppConstant.PRODUCT_STATUS_VALUE);
        edtStatus.setText(status);
        edtStatus.setTextColor(Utils.getColorWrapper(this, R.color.red_dark));
    }

    private void setResultFromProductCategory(Intent data) {
        ProductCategoryResponse response = data.getParcelableExtra(AppConstant.CATEGORY);
        edtCategory.setText(response.getName());
        edtCategory.setTextColor(Utils.getColorWrapper(this, R.color.red_dark));
        // TODO: show dimension, weight, brand if have
    }

    private void setResultFromProductSellAddress(Intent data) {

    }

    private void initFocusListenerForEditText() {
        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()) {
                    case R.id.edtName:
                        editFocusPosition = 1;
                        break;
                    case R.id.edtDescription:
                        editFocusPosition = 2;
                        break;
                    case R.id.edtBuyPrice:
                        editFocusPosition = 3;
                        break;
                }
                topKeyboardLayout.setVisibility(View.VISIBLE);
            }
        };
        edtName.setOnFocusChangeListener(listener);
        edtDescription.setOnFocusChangeListener(listener);
        edtBuyPrice.setOnFocusChangeListener(listener);
    }

    private void initTextChangeListener() {
        edtBuyPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    String ss = s.toString();
                    Integer price = Integer.parseInt(ss);
//                    String priceStr = Utils.formatPrice2(String.valueOf(price * 1000));
//                    edtBuyPrice.setText(price.toString());
                    txtTotal.setText(price.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override()
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.nextBtn:
                        if (editFocusPosition == 1) {
                            edtDescription.requestFocus();
                        } else if (editFocusPosition == 2) {
                            edtBuyPrice.requestFocus();
                        }
                        break;
                    case R.id.preBtn:
                        if (editFocusPosition == 3) {
                            edtDescription.requestFocus();
                        } else if (editFocusPosition == 2) {
                            edtName.requestFocus();
                        }
                        break;
                    case R.id.doneBtn:
                        edtName.clearFocus();
                        edtDescription.clearFocus();
                        edtBuyPrice.clearFocus();
                        Utils.hideSoftKeyboard(AddProductActivity.this);
                        topKeyboardLayout.setVisibility(View.GONE);
                        break;
                    case R.id.edtCategory:
                        intent = new Intent(BaseApp.getContext(), ProductCategoryActivity.class);
                        startActivityForResult(intent, REQUEST_PRODUCT_CATEGORY);
                        break;
                    case R.id.edtStatus:
                        intent = new Intent(BaseApp.getContext(), ProductStatusActivity.class);
                        startActivityForResult(intent, REQUEST_PRODUCT_STATUS);
                        break;
                    case R.id.edtSellAddress:
                        intent = new Intent(BaseApp.getContext(), ProductSellAddressActivity.class);
                        startActivityForResult(intent, REQUEST_SELL_ADDRESS);
                        break;
                }
            }
        };
        btnNext.setOnClickListener(listener);
        btnPrev.setOnClickListener(listener);
        btnDone.setOnClickListener(listener);
        edtCategory.setOnClickListener(listener);
        edtStatus.setOnClickListener(listener);
        edtSellAddress.setOnClickListener(listener);
    }

}
