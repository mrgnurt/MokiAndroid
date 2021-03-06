package com.coho.moki.ui.product.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.coho.moki.data.remote.ProductResponseData;
import com.coho.moki.data.remote.ProductSellAddressResponse;
import com.coho.moki.service.ProductService;
import com.coho.moki.service.ProductServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.brand_search.SearchBrandActivity;
import com.coho.moki.ui.login.LoginActivity;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.WheelViewAdapter;

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
    private String categoryId = null;
    private Integer condition = -1;
    private List<Integer> shipsFromId = new ArrayList<>();
    private String defaultShipFrom = "Số 10, Phường Bách Khoa, Quận Hai Bà Trưng, TP.Hà Nội";

    ProductService mProductService;
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
    private String[] dimens = new String[2];

    private int imgPos;
    private List<Uri> uriList = new ArrayList<>();

    @Override
    public int setContentViewId() {
        return R.layout.add_product;
    }

    @Override
    public void initView() {
        mProductService = new ProductServiceImpl();
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
        initServiceArticle();
        initListenerChooseImage();
        initFocusListenerForEditText();
        initClickListener();
        initTextChangeListener();
        initListenerForSwitchButton();
        switchAutoAccept.setChecked(true);
        topKeyboardLayout.setVisibility(View.GONE);
        edtSellAddress.setText(defaultShipFrom);
    }

    private void initServiceArticle() {
        txtServiceArticle.setText(R.string.service_article);
        String content = txtServiceArticle.getText().toString();
        SpannableString ss = new SpannableString(content);
        int startIndex = content.indexOf("C");
        int endIndex = content.indexOf("I") + 1;
        ss.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.red_dark)), startIndex, endIndex, 0);
        txtServiceArticle.setMovementMethod(LinkMovementMethod.getInstance());
        txtServiceArticle.setText(content, TextView.BufferType.SPANNABLE);
        Spannable spannable = (Spannable) txtServiceArticle.getText();
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.d(TAG, "clicked on txtServiceArticle");
            }
        };
        spannable.setSpan(clickableSpan, startIndex, endIndex, 0);
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
        if (status.equals("Mới")) {
            condition = 0;
        } else if (status.equals("Gần như mới")) {
            condition = 1;
        } else if (status.equals("Tốt")) {
            condition = 2;
        } else if (status.equals("Khá tốt")) {
            condition = 3;
        } else {
            condition = 4;
        }
        edtStatus.setText(status);
        edtStatus.setTextColor(Utils.getColorWrapper(this, R.color.red_dark));
    }

    private void setResultFromProductCategory(Intent data) {
        ProductCategoryResponse response = data.getParcelableExtra(AppConstant.CATEGORY);
        categoryId = response.getId();
        edtCategory.setText(response.getName());
        edtCategory.setTextColor(Utils.getColorWrapper(this, R.color.red_dark));
        // TODO: show dimension, weight, brand if have
        // if have weight
        if (response.getHasBrand() == 1) {
            llBrand.setVisibility(View.VISIBLE);
        } else {
            llBrand.setVisibility(View.GONE);
        }

        if (response.getHasSize() == 1) {
            llSize.setVisibility(View.VISIBLE);
        } else {
            llSize.setVisibility(View.GONE);
        }

        llWeight.setVisibility(View.VISIBLE);
    }

    private void setResultFromProductSellAddress(Intent data) {
        edtSellAddress.setText(defaultShipFrom);
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
                    case R.id.edtBrand:
                        // TODO: goto brand activity
//                        intent = new Intent(BaseApp.getContext(), SearchBrandActivity.class);

                        break;
                    case R.id.edtSize:
                        // TODO: goto size activity
                        break;
                    case R.id.edtDimension:
                        showDimensionDialog();
                        break;
                    case R.id.edtWeight:
                        showWeightDialog();
                        break;
                    case R.id.btnAddProduct:
                        addProduct();
                        break;
                    case R.id.txtServiceArticle:

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
        edtWeight.setOnClickListener(listener);
        edtDimension.setOnClickListener(listener);
        edtBrand.setOnClickListener(listener);
        edtDimension.setOnClickListener(listener);
        btnAddProduct.setOnClickListener(listener);
    }

    private void initListenerForSwitchButton() {
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId()) {
                    case R.id.switch_product_type: // free
                        if (isChecked) {
                            llWeight.setVisibility(View.VISIBLE);
                            llDimension.setVisibility(View.VISIBLE);
                            // TODO: hide service fee and total money
                        } else {
                            llWeight.setVisibility(View.GONE);
                            llDimension.setVisibility(View.GONE);
                            // TODO: show service fee and total money
                        }
                        break;
                    case R.id.switch_auto_accept: // fast sell

                        break;
                    case R.id.switch_allow_offer: // allow bargain

                        break;
                }
            }
        };
        switchProductType.setOnCheckedChangeListener(listener);
        switchAutoAccept.setOnCheckedChangeListener(listener);
        switchAllowOffer.setOnCheckedChangeListener(listener);
    }

    private void showDimensionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dimension_dialog);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.DialogAnimation);
        // bind view
        Button btnExit = dialog.findViewById(R.id.btnExit);
        final TextView txtSelectedDimension = dialog.findViewById(R.id.selectedDimension);
        Button btnChoose = dialog.findViewById(R.id.btnChoose);
        final WheelView lengthWheel = dialog.findViewById(R.id.lengthWheel);
        final WheelView widthWheel = dialog.findViewById(R.id.widthWheel);
        final WheelView heightWheel = dialog.findViewById(R.id.heightWheel);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.btnExit:
                    case R.id.btnChoose:
                        edtDimension.setText(dimens[0] + " x " + dimens[1] + " x " + dimens[2]);
                        dialog.dismiss();
                }
            }
        };
        btnExit.setOnClickListener(clickListener);
        btnChoose.setOnClickListener(clickListener);
        final List<String> dataList = createDimensionList();
        lengthWheel.setVisibleItems(5);
        widthWheel.setVisibleItems(5);
        heightWheel.setVisibleItems(5);
        String[] data = new String[dataList.size()];
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(this, dataList.toArray(data));
        lengthWheel.setCurrentItem(1);
        lengthWheel.setViewAdapter(adapter);
        widthWheel.setCurrentItem(1);
        widthWheel.setViewAdapter(adapter);
        heightWheel.setCurrentItem(1);
        adapter.setTextSize(14);
        heightWheel.setViewAdapter(adapter);
        txtSelectedDimension.setText("1cm dài x 1cm rộng x 1cm cao");
        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                dimens[0] = dataList.get(lengthWheel.getCurrentItem());
                dimens[1] = dataList.get(widthWheel.getCurrentItem());
                dimens[2] = dataList.get(heightWheel.getCurrentItem());
                txtSelectedDimension.setText(dimens[0] + " dài x " +
                        dimens[1] + " rộng x " +
                        dimens[2] + " cao"
                );
            }
        };
        lengthWheel.addScrollingListener(scrollListener);
        widthWheel.addScrollingListener(scrollListener);
        heightWheel.addScrollingListener(scrollListener);
        dialog.show();
    }

    private void showWeightDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.weight_dialog);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.DialogAnimation);
        // bind view
        Button btnExit = dialog.findViewById(R.id.btnExit);
        Button btnChoose = dialog.findViewById(R.id.btnChoose);
        final WheelView wheelView = dialog.findViewById(R.id.weightWheel);
        final List<String> weightList = createWeightList();
        wheelView.setVisibleItems(5);
        String[] weightArr = new String[weightList.size()];
        ArrayWheelAdapter<String> weightAdapter = new ArrayWheelAdapter<>(this, weightList.toArray(weightArr));
        wheelView.setCurrentItem(1);
        weightAdapter.setTextSize(14);
        wheelView.setViewAdapter(weightAdapter);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnExit:
                    case R.id.btnChoose:
                        edtWeight.setText(weightList.get(wheelView.getCurrentItem()));
                        dialog.dismiss();
                        break;
                }
            }
        };
        btnExit.setOnClickListener(clickListener);
        btnChoose.setOnClickListener(clickListener);
        dialog.show();
    }

    private List<String> createWeightList() {
        List<String> weightList = new ArrayList<>();
        for (int i = 0; i < 40; ++i) {
            String medium = Utils.formatWeight(String.valueOf(i + 0.5));
            weightList.add("Trên " + i + "kg đến " + medium + "kg");
            weightList.add("Trên " + medium + "kg đến " + (i+1) + "kg");
        }
        return weightList;
    }

    private List<String> createDimensionList() {
        List<String> dimensionList = new ArrayList<>();
        for (int i = 1; i <= 200; ++i) {
            dimensionList.add(i + "cm");
        }
        return dimensionList;
    }

    private void addProduct() {
        String token = AccountUntil.getUserToken();
        if (token != null) {
            String name = edtName.getText().toString() + "";
            int price = 0;
            try {
                price = Integer.parseInt(edtBuyPrice.getText().toString());
            } catch(Exception e) {

            }
            String productSizeId = null;
            String brandId = null;
            String described = edtDescription.getText().toString() + "";
            String shipsFrom = "Số 10";
            shipsFromId.add(1);
            shipsFromId.add(2);
            shipsFromId.add(3);
            String weight = edtWeight.getText().toString();
            if (name == "") {
                DialogUtil.showPopupError(AddProductActivity.this, "Chưa nhập tên sản phẩm");
            } else if (described == "") {
                DialogUtil.showPopupError(AddProductActivity.this, "Chưa nhập mô tả");
            }  else if (categoryId == null) {
                DialogUtil.showPopupError(AddProductActivity.this, "Chưa chọn loại sản phẩm");
            } else if (condition == -1) {
                DialogUtil.showPopupError(AddProductActivity.this, "Chưa chọn trạng thái sản phẩm");
            } else if (price == 0) {
                DialogUtil.showPopupError(AddProductActivity.this, "Chưa nhập giá sản phẩm");
            } else {
                DialogUtil.showProgress(AddProductActivity.this);
                mProductService.addProduct(token, name, price,
                        productSizeId, brandId, categoryId, uriList,
                        null, null, described, shipsFrom, shipsFromId,
                        condition, null, weight,
                        new ResponseListener<ProductResponseData>() {
                            @Override
                            public void onSuccess(ProductResponseData dataResponse) {
                                DialogUtil.hideProgress();
                                DialogUtil.showPopupSuccess(AddProductActivity.this, "Thêm sản phẩm thành công");
                                Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(String errorMessage) {
                                Log.d("ErrorAdd", errorMessage);
                                DialogUtil.hideProgress();
                                DialogUtil.showPopupError(AddProductActivity.this, "Thêm sản phẩm thất bại");
                            }
                        });
            }
        } else {
            Intent intent = new Intent(AddProductActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.btnNavLeft)
    public void finishActivity() {
        onBackPressed();
    }
}
