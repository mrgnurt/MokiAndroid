package com.coho.moki.ui.product;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.coho.moki.BaseApp;
import com.coho.moki.adapter.product.ProductCommentAdapter;
import com.coho.moki.adapter.product.ProductImageAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.ProductComment;
import com.coho.moki.data.remote.LikeResponseData;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;
import com.coho.moki.ui.base.BaseActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IntegerRes;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coho.moki.ui.login.LoginActivity;
import com.coho.moki.ui.user.UserInfoActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.coho.moki.util.network.LoadImageUtils;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import com.coho.moki.R;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import javax.inject.Inject;


/**
 * Created by Khanh Nguyen on 10/12/2017.
 */

public class ProductDetailActivity extends BaseActivity implements ProductDetailView {

    @Inject
    ProductDetailPresenter mProductDetailPresenter;

    private static final String TAG = "ProductDetailActivity";
    private boolean isExpand = false;
    private static final Integer DESCRIPTION_NO_EXPAND_MAX = 2;
    private static final Integer DESCRIPTION_EXPAND_MAX = 40;
    private boolean isFirstSetDocument = true;
    private boolean isLiked = false;

    // bind view from product_detail
//    @BindView(R.id.bottom_sheet)
//    BottomSheetLayout bottomSheet;

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    @BindView(R.id.product_layout)
    LinearLayout productLayout;

    @BindView(R.id.scroll_view)
    PullToZoomScrollViewEx scrollView;

    @BindView(R.id.bottomBar)
    LinearLayout bottomBar;

    @BindView(R.id.txtPrice)
    TextView txtPrice;

    @BindView(R.id.salePrice)
    TextView salePrice;

    @BindView(R.id.btnBuy)
    Button btnBuy;

    private ScrollView refreshScrollView;
    private TextView txtLike;
    private TextView txtComment;
    private AppCompatImageView imgLike;
    private ImageView imgAvatar;
    private TextView txtName;
    private TextView txtScore;
    private TextView txtProduct;
    private TextView txtDescription;
    private DocumentView dvDescription;
    private ImageView iconClock;
    private TextView txtTime;
    private TextView txtExpandable;
    private LinearLayout llCategory;
    private LinearLayout llSize;
    private TextView txtSize;
    private LinearLayout llWeight;
    private TextView txtWeight;
    private LinearLayout llDimension;
    private TextView txtDimension;
    private LinearLayout llBrand;
    private TextView txtBrand;
    private LinearLayout llProductStatus;
    private TextView txtProductStatus;
    private TextView txtBuyPlace;
    private Button btnViewComment;
    private TextView txtCategory;
    private ImageView imgThumb;
    private Button btnPrevious;
    private Button btnNext;
    private FrameLayout frameImage;
    private ViewPager viewPager;
    //    private ListView listComment;
    private ExpandableHeightListView listComment;
    private ImageView iconNextArrow;
    private LinearLayout layoutUserInfo;
    private LinearLayout llCategoryParent;

    private String productId;
    private String token;

    private String mProductAvatar;

    private String mPartnerId;

    private String mPartnerAvatar;

    private String mPartnerUsername;

//    private String mSellerName;
//
//    private String mSellerAvatar;

    private String mSellerId;

    private boolean isOwnerProduct;


    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtHeaderRunning)
    TextView txtHeaderRunning;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnMenu;

    @Override
    public int setContentViewId() {
        return R.layout.product_detail;
    }

    @Override
    public void initView() {
        Log.d(TAG, "initView");
        BaseApp.getActivityComponent().inject(this);
        mProductDetailPresenter.onAttach(this);
        loadViewForCode();
        Intent intent = getIntent();
        productId = intent.getStringExtra(AppConstant.PRODUCT_ID);
        token = AccountUntil.getUserToken();

        if (AccountUntil.getAccountId() == null) {
            btnBuy.setClickable(false);
        }

//        ActionBar mActionBar = getSupportActionBar();  //to support lower version too
//        mActionBar.setDisplayShowHomeEnabled(false);
//        mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        View mCustomView = mInflater.inflate(R.layout.navigation_bar, null);
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);

        // load data from API

        // load data from intent
    }

    @Override
    public void initData() {
    }

    private void initFakeData() {
        txtPrice.setText("900,000 VNĐ");
        btnBuy.setText("Mua");
        btnBuy.setVisibility(View.VISIBLE);
        txtLike.setText("0");
        txtComment.setText("1");
        imgLike.setImageResource(R.drawable.icon_like_on);
        imgAvatar.setImageResource(R.drawable.unknown_user);
        txtName.setText("mamichery");
        txtScore.setText(": 0");
        txtProduct.setText(": 3");
        txtTime.setText("2 giờ trước");

        txtExpandable.setVisibility(View.VISIBLE);
//        dvDescription.setText("dfidgfidgjfg ifgfg fdfd");
        dvDescription.setText("Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao - Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao" +
                "Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao - Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao"
        );
        dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_EXPAND_MAX);
        dvDescription.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int i = 0;
        llCategory.removeAllViews();
        String str = "Bé chơi mà học";
        while (i < 3) {
            txtCategory = new TextView(this);
            if (i == 1) {
                str = "Hàng Việt Nam chất lượng cao";
            }
            txtCategory.setText(str);
            txtCategory.setTextColor(Utils.getColorWrapper(this, R.color.red_dark));
            txtCategory.setBackgroundResource(R.drawable.layout_transparent_bg_red_corner);
            txtCategory.setPadding(20, 10, 20, 10);
            layoutParams.setMargins(0, 5, 0, 5);
            txtCategory.setLayoutParams(layoutParams);
            llCategory.addView(this.txtCategory);
            ++i;
        }

        txtSize.setText("Size M");
        llSize.setVisibility(View.VISIBLE);

        txtWeight.setText("Trên 0kg đến 0.5kg");
        llWeight.setVisibility(View.VISIBLE);

        txtDimension.setText("10cm x 15cm x 6 cm");
        llDimension.setVisibility(View.VISIBLE);

        txtBrand.setText("Adidas");
        txtBrand.setPadding(20, 10, 20, 10);
        txtBrand.setTextColor(Utils.getColorWrapper(this, R.color.red_dark));
        txtBrand.setBackgroundResource(R.drawable.layout_transparent_bg_red_corner);
        txtBrand.setLayoutParams(layoutParams);
        llBrand.setVisibility(View.VISIBLE);

        txtProductStatus.setText("Còn mới");
        txtBuyPlace.setText("Hai Bà Trưng - Hà Nội");
        txtHeaderRunning.setText("Gấu Bông Chất Lượng Cao - Gấu Bông Chất Lượng Cao");
        txtHeaderRunning.setSelected(true);

        salePrice.setVisibility(View.VISIBLE);
        salePrice.setText("1,000,000 VNĐ");
        salePrice.setPaintFlags(salePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    private void loadViewForCode() {
        View headView = LayoutInflater.from(this).inflate(R.layout.product_detail_head, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.product_detail_zoom, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.product_detail_content, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);

        View rootView = scrollView.getPullRootView();

        // load view from content view
        txtLike = rootView.findViewById(R.id.txtLike);
        txtComment = rootView.findViewById(R.id.txtComment);
        imgLike = rootView.findViewById(R.id.imgLike);
        imgAvatar = rootView.findViewById(R.id.imgAvatar);

        txtName = rootView.findViewById(R.id.txtName);
        txtScore = rootView.findViewById(R.id.txtScore);
        txtProduct = rootView.findViewById(R.id.txtProduct);
        dvDescription = rootView.findViewById(R.id.dvDescription);
        txtExpandable = rootView.findViewById(R.id.txtExpandable);
        txtTime = rootView.findViewById(R.id.txtTime);
        btnViewComment = rootView.findViewById(R.id.btnViewComment);
        llCategory = rootView.findViewById(R.id.llCategory);
        llBrand = rootView.findViewById(R.id.llBrand);
        txtBrand = rootView.findViewById(R.id.txtBrand);
        llSize = rootView.findViewById(R.id.llSize);
        txtSize = rootView.findViewById(R.id.txtSize);
        llWeight = rootView.findViewById(R.id.llWeight);
        txtWeight = rootView.findViewById(R.id.txtWeight);
        llDimension = rootView.findViewById(R.id.llDimension);
        txtDimension = rootView.findViewById(R.id.txtDimension);
        llProductStatus = rootView.findViewById(R.id.llProductStatus);
        txtProductStatus = rootView.findViewById(R.id.txtProductStatus);
        txtBuyPlace = rootView.findViewById(R.id.txtBuyPlace);
        imgThumb = rootView.findViewById(R.id.imgThumb);
        txtDescription = rootView.findViewById(R.id.txtDescription);
        btnPrevious = rootView.findViewById(R.id.btnPrevious);
        btnNext = rootView.findViewById(R.id.btnNext);
        iconClock = rootView.findViewById(R.id.icon_clock);
        iconNextArrow = rootView.findViewById(R.id.icon_next_arrow);
        layoutUserInfo = rootView.findViewById(R.id.layout_user_info);
        llCategoryParent = rootView.findViewById(R.id.llCategoryParent);

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int screenHeight = localDisplayMetrics.heightPixels;
        int sccreenWidth = localDisplayMetrics.widthPixels;
//        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
//        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, mScreenHeight);
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(sccreenWidth, sccreenWidth);
        scrollView.setHeaderLayoutParams(localObject);

//         load view from zoom view
        frameImage = rootView.findViewById(R.id.frameImage);
        frameImage.setVisibility(View.VISIBLE);
        viewPager = rootView.findViewById(R.id.viewPager);
        btnPrevious = rootView.findViewById(R.id.btnPrevious);
        btnNext = rootView.findViewById(R.id.btnNext);

        listComment = rootView.findViewById(R.id.listComment);

        layoutUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickUserInfo();
            }
        });
        txtExpandable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    txtExpandable.setText(getResources().getString(R.string.collapse));
                    dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_EXPAND_MAX);
                    dvDescription.requestLayout();
                    isExpand = true;
                } else {
                    txtExpandable.setText(getResources().getString(R.string.view_more));
                    dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_NO_EXPAND_MAX);
                    dvDescription.requestLayout();
                    isExpand = false;
                }
            }
        });


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AccountUntil.getAccountId().equals(mSellerId)) {
                    Log.d(TAG, "La seller coi sp chinh no");
                    return;
                }

                Intent intent = new Intent(ProductDetailActivity.this, ProductChatActivity.class);
                // put data before switch activity
                Bundle data = new Bundle();
                data.putString(AppConstant.PRODUCT_ID_CHAT_TAG, productId);
                data.putString(AppConstant.PRODUCT_AVATAR_CHAT_TAG, mProductAvatar);
                data.putString(AppConstant.PARTNER_ID_CHAT_TAG, mPartnerId);
                data.putString(AppConstant.PARTNER_USERNAME_CHAT_TAG, mPartnerUsername);
                data.putString(AppConstant.PARTNER_AVATAR_CHAT_TAG, mPartnerAvatar);

                intent.putExtra(AppConstant.PACKAGE_TAG, data);
                startActivity(intent);
            }
        });

        dvDescription.setOnLayoutProgressListener(new DocumentView.ILayoutProgressListener() {
            @Override
            public void onCancelled() {
                Log.d(TAG, "dv cancel");
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "dv finish");
                if (isFirstSetDocument) {
                    Log.d(TAG, "dv update : line count : " + dvDescription.getLayout().getLineCount());
                    if (dvDescription.getLayout().getLineCount() > 2) {
                        txtExpandable.setVisibility(View.VISIBLE);
                        dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_NO_EXPAND_MAX);
                        dvDescription.requestLayout();
                    } else {
                        txtExpandable.setVisibility(View.INVISIBLE);
                    }
                    isFirstSetDocument = false;
                }
            }

            @Override
            public void onStart() {
                Log.d(TAG, "dv start");
            }

            @Override
            public void onProgressUpdate(float progress) {
                Log.d(TAG, "dv update");
            }
        });

        btnViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, ProductCommentActivity.class);
                intent.putExtra(AppConstant.PRODUCT_ID, productId);
                DialogUtil.showProgress(ProductDetailActivity.this);
                startActivity(intent);
            }
        });


        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showProgress(ProductDetailActivity.this);
                if (token == null) {
                    ProductDetailActivity.this.finish();
                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    mProductDetailPresenter.likeProductRemote(token, productId);
                }
            }
        });
    }

    private void clickUserInfo() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        DialogUtil.showProgress(this);
        startActivity(intent);
    }

    @OnClick(R.id.btnNavRight)
    public void showCustomDialog() {
//        final Dialog dialog = new Dialog(this, R.style.full_screen_dialog); // other solution for full screen dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product_detail);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.DialogAnimation);
        View.OnClickListener closeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
        View viewTop = dialog.findViewById(R.id.viewTop);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        viewTop.setOnClickListener(closeListener);
        btnCancel.setOnClickListener(closeListener);
        dialog.show();
    }

    @Override
    public void setData(final ProductDetailResponse response) {
        txtLike.setText(response.getLike().toString());
        txtComment.setText(response.getComment().toString());
        if (response.getIsLiked() == 0) {
            LoadImageUtils.loadImageFromDrawable(R.drawable.icon_like_off, imgLike);
            isLiked = false;
        } else {
            LoadImageUtils.loadImageFromDrawable(R.drawable.icon_like_on, imgLike);
            isLiked = true;
        }
        ProductDetailResponse.Seller seller = response.getSeller();

        ///////////////////////////////////////////////////
        mPartnerId = seller.getId();
        mPartnerAvatar = seller.getAvatar();
        mPartnerUsername = seller.getName();
        mSellerId = seller.getId();
//        mSellerAvatar = seller.getAvatar();
//        mSellerName = seller.getName();
        mProductAvatar = response.getImage().get(0).getUrl();

        /////////////////////////////////////////////////


        txtName.setText(seller.getName());
        LoadImageUtils.loadImageFromUrl(seller.getAvatar(), R.drawable.unknown_user, imgAvatar, null);
        txtScore.setText(": " + response.getSeller().getScore());
        txtProduct.setText(": " + response.getSeller().getListing());
        txtExpandable.setVisibility(View.VISIBLE);

        dvDescription.setText("Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao - Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao" +
                "Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao - Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao"
        );
        dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_EXPAND_MAX);
        dvDescription.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);

//        dvDescription.setText(response.getDescribed());
//        dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_EXPAND_MAX);
//        dvDescription.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
//        dvDescription.requestLayout();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtTime.setText(Utils.formatTime(response.getCreated()));
        Integer pricePercent = response.getPricePercent();
        Integer price = response.getPrice();
        if (price == null || price == 0) {
            txtPrice.setText(R.string.free);
            salePrice.setVisibility(View.INVISIBLE);
        } else if (pricePercent != null && pricePercent != 0) {
            txtPrice.setText(Utils.formatPrice(pricePercent.toString()));
            salePrice.setPaintFlags(salePrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            salePrice.setText(Utils.formatPrice(price.toString()));
        } else {
            txtPrice.setText(Utils.formatPrice(price.toString()));
            salePrice.setVisibility(View.INVISIBLE);
        }
        Integer canEdit = response.getCanEdit();
        if (seller.getId().equals(AccountUntil.getAccountId())) {
            btnBuy.setText(R.string.edit);
            btnBuy.setVisibility(View.VISIBLE);
            btnBuy.setBackgroundResource(R.color.green_status);
        } else if (response.getIsBlocked() == 1) {
            btnBuy.setVisibility(View.GONE);
        } else {
            btnBuy.setText("Mua");
            btnBuy.setVisibility(View.VISIBLE);
            btnBuy.setBackgroundResource(R.color.red_dark);
        }

        if (response.getComment() == 0) {
            listComment.setVisibility(View.GONE);
            btnViewComment.setText(R.string.the_first_comment);
        } else {
            listComment.setVisibility(View.VISIBLE);
            btnViewComment.setText(R.string.view_all_comment);
        }
        txtBuyPlace.setText(response.getShipsFrom());
        ProductDetailResponse.Dimension dimension = response.getDimension();
        if (dimension == null) {
            llDimension.setVisibility(View.GONE);
        } else {
            llDimension.setVisibility(View.VISIBLE);
            StringBuilder d = new StringBuilder();
            d.append(dimension.getLength())
                    .append("cm x ")
                    .append(dimension.getWidth())
                    .append("cm x ")
                    .append(dimension.getHeight())
                    .append("cm");
            txtDimension.setText(d.toString());
            txtDimension.setVisibility(View.VISIBLE);
        }
        List<ProductDetailResponse.Category> categoryList = response.getCategory();
        if (categoryList != null && categoryList.size() > 0) {
            llCategory.setVisibility(View.VISIBLE);
            llCategory.removeAllViews();
            int listSize = categoryList.size();
            for (int i = 0; i < listSize; ++i) {
                txtCategory = new TextView(this);
                txtCategory.setText(categoryList.get(i).getName());
                txtCategory.setTextColor(Utils.getColorWrapper(this, R.color.red_dark));
                txtCategory.setBackgroundResource(R.drawable.layout_transparent_bg_red_corner);
                txtCategory.setPadding(20, 10, 20, 10);
                layoutParams.setMargins(0, 5, 0, 5);
                txtCategory.setLayoutParams(layoutParams);
                llCategory.addView(this.txtCategory);
            }
        } else {
            llCategoryParent.setVisibility(View.GONE);
//            llCategory.setVisibility(View.GONE);
        }
        List<ProductDetailResponse.Brand> brandList = response.getBrand();
        if (brandList != null && brandList.size() > 0) {
            txtBrand.setText(brandList.get(0).getName());
            txtBrand.setPadding(20, 10, 20, 10);
            txtBrand.setTextColor(Utils.getColorWrapper(this, R.color.red_dark));
            txtBrand.setBackgroundResource(R.drawable.layout_transparent_bg_red_corner);
            txtBrand.setLayoutParams(layoutParams);
            llBrand.setVisibility(View.VISIBLE);
        } else {
            llBrand.setVisibility(View.GONE);
        }
        txtHeader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final int lineCount = txtHeader.getLayout().getLineCount();
                if (lineCount > 1) {
                    txtHeaderRunning.setText(Utils.toTitleCase(response.getName()));
                    txtHeaderRunning.setVisibility(View.VISIBLE);
                    txtHeaderRunning.setSelected(true);
                    txtHeader.setVisibility(View.GONE);
                } else {
                    txtHeaderRunning.setVisibility(View.GONE);
                }
            }
        });
        txtHeader.setText(Utils.toTitleCase(response.getName()));
        List<ProductDetailResponse.Size> size = response.getSize();
        if (size != null && size.size() > 0) {
            txtSize.setText(size.get(0).getName());
            txtSize.setVisibility(View.VISIBLE);
            llSize.setVisibility(View.VISIBLE);
        } else {
            llSize.setVisibility(View.GONE);
        }
        String weight = response.getWeight();
        if (weight != null) {
            txtWeight.setText(weight + " kg");
            txtWeight.setVisibility(View.VISIBLE);
            llWeight.setVisibility(View.VISIBLE);
        } else {
            llWeight.setVisibility(View.GONE);
        }
        List<ProductDetailResponse.Image> imgList = response.getImage();
        if (imgList != null && imgList.size() > 0) {
            List<String> urlList = new ArrayList<>();
            for (int i  = 0; i < imgList.size(); ++i) {
                urlList.add(imgList.get(i).getUrl());
            }
            loadImageViewPager(urlList);
        }
        String condition = response.getCondition();
        if (condition != null) {
            txtProductStatus.setText(condition);
            llProductStatus.setVisibility(View.VISIBLE);
        } else {
            llProductStatus.setVisibility(View.GONE);
        }
    }

    private void loadImageViewPager(List<String> urlList) {
        final List<String> imgList = urlList;
        viewPager.setAdapter(new ProductImageAdapter(getSupportFragmentManager(), imgList));

        if (imgList != null && imgList.size() > 1) {

            btnNext.setVisibility(View.VISIBLE);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        btnPrevious.setVisibility(View.INVISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
                    } else if (position == imgList.size() - 1) {
                        btnNext.setVisibility(View.INVISIBLE);
                        btnPrevious.setVisibility(View.VISIBLE);
                    } else {
                        btnPrevious.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            btnPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                }
            });

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
            });
        }
    }

    @Override
    public void setProductComment(List<ProductCommentResponse > commentList) {
        if (commentList != null && commentList.size() > 0) {
            Collections.reverse(commentList);
            int listSize = commentList.size();
            listSize = listSize > 3 ? 3 : listSize;
            ProductCommentAdapter commentAdapter = new ProductCommentAdapter(
                    this, R.layout.product_comment_item, commentList.subList(0, listSize));
            listComment.setAdapter(commentAdapter);
//         This actually do the magic
            listComment.setExpanded(true);
        }
    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonNavLeft() {
        onBackPressed();
    }

    @Override
    public void setLikeProduct(LikeResponseData likeResponseData) {
        if (isLiked == false) {
            imgLike.setImageResource(R.drawable.icon_like_on);
            isLiked = true;
        } else {
            imgLike.setImageResource(R.drawable.icon_like_off);
            isLiked = false;
        }
        txtLike.setText(likeResponseData.getLike().toString());
    }

    @Override
    public void onResume() {
        mProductDetailPresenter.getProductDetailRemote(token, productId);
        mProductDetailPresenter.getProductCommentRemote(productId);
        super.onResume();
    }

}
