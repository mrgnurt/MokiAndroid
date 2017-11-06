package com.coho.moki.ui.product;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.coho.moki.BaseApp;
import com.coho.moki.adapter.product.ProductCommentAdapter;
import com.coho.moki.adapter.product.ProductImageAdapter;
import com.coho.moki.data.model.ProductComment;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.data.remote.ProductDetailResponse;
import com.coho.moki.ui.base.BaseActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coho.moki.ui.user.UserInfoActivity;
import com.coho.moki.util.Utils;
import com.coho.moki.util.network.LoadImageUtils;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.coho.moki.R;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;


/**
 * Created by Khanh Nguyen on 10/12/2017.
 */

public class ProductDetailActivity extends BaseActivity implements ProductDetailView {

    @Inject
    ProductDetailPresenter mProductDetailPresenter;

    private static final String LOG_TAG = ProductDetailActivity.class.getSimpleName();
    private boolean isExpand = false;
    private static final Integer DESCRIPTION_NO_EXPAND_MAX = 2;
    private static final Integer DESCRIPTION_EXPAND_MAX = 40;
    private boolean isFirstSetDocument = true;

    // bind view from product_detail
    @BindView(R.id.bottom_sheet)
    BottomSheetLayout bottomSheet;

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
        BaseApp.getActivityComponent().inject(this);
        mProductDetailPresenter.onAttach(this);
        loadViewForCode();

//        ActionBar mActionBar = getSupportActionBar();  //to support lower version too
//        mActionBar.setDisplayShowHomeEnabled(false);
//        mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        View mCustomView = mInflater.inflate(R.layout.navigation_bar, null);
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);

        // load data from API

        // load data from intent
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc0xvZ2luIjp0cnVlLCJ1c2VyIjp7ImlkIjoiNTllOTZhODhmZTAzODgzMGVmYzE1MzgxIiwidXNlcm5hbWUiOiJBcmVseSBCZWF0dHkiLCJwaG9uZU51bWJlciI6IjUwNi45NzUuMzA4NCIsInJvbGUiOjEsInVybCI6Imh0dHBzOi8vb3Jpb24uY29tIn19.5ExdMHvowsh_hSmDTTsicUBV5xaICczbiFKMa0MF2eI";
        productId = "5a0073061ca2a017f87d656f";
    }

    @Override
    public void initData() {
        initFakeData();
        mProductDetailPresenter.getProductDetailRemote(token, productId);
        mProductDetailPresenter.getProductCommentRemote(productId);
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

//        dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_EXPAND_MAX);
//        dvDescription.requestLayout();
//        Log.d(LOG_TAG, "dv line count before: " + dvDescription.getLayout().getLineCount());

        // check this code again
//        dvDescription.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                Log.d(LOG_TAG, "dv line count: " + dvDescription.getLayout().getLineCount());
//                if (dvDescription.getLayout().getLineCount() <= 2) {
//                    txtExpandable.setVisibility(View.INVISIBLE);
//                } else {
//                    txtExpandable.setVisibility(View.VISIBLE);
//                }
//                dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTON_NO_EXPAND_MAX);
//                dvDescription.requestLayout();
//                Log.d(LOG_TAG, "dv line count after: " + dvDescription.getLayout().getLineCount());
//                // after first call, remove listener on dvDescription
//                dvDescription.removeOnLayoutChangeListener(this);
//            }
//        });

        dvDescription.setOnLayoutProgressListener(new DocumentView.ILayoutProgressListener() {
            @Override
            public void onCancelled() {
                Log.d(LOG_TAG, "dv cancel");
            }

            @Override
            public void onFinish() {
                Log.d(LOG_TAG, "dv finish");
                if (isFirstSetDocument) {
                    Log.d(LOG_TAG, "dv update : line count : " + dvDescription.getLayout().getLineCount());
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
                Log.d(LOG_TAG, "dv start");
            }

            @Override
            public void onProgressUpdate(float progress) {
                Log.d(LOG_TAG, "dv update");
            }
        });

    }

    private void loadViewForCode() {
        View headView = LayoutInflater.from(this).inflate(R.layout.product_detail_head, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.product_detail_zoom, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.product_detail_content, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);

        View mRootView = scrollView.getPullRootView();

        // load view from content view
        txtLike = mRootView.findViewById(R.id.txtLike);
        txtComment = mRootView.findViewById(R.id.txtComment);
        imgLike = mRootView.findViewById(R.id.imgLike);
        imgAvatar = mRootView.findViewById(R.id.imgAvatar);

        txtName = mRootView.findViewById(R.id.txtName);
        txtScore = mRootView.findViewById(R.id.txtScore);
        txtProduct = mRootView.findViewById(R.id.txtProduct);
        dvDescription = mRootView.findViewById(R.id.dvDescription);
        txtExpandable = mRootView.findViewById(R.id.txtExpandable);
        txtTime = mRootView.findViewById(R.id.txtTime);
        btnViewComment = mRootView.findViewById(R.id.btnViewComment);
        llCategory = mRootView.findViewById(R.id.llCategory);
        llBrand = mRootView.findViewById(R.id.llBrand);
        txtBrand = mRootView.findViewById(R.id.txtBrand);
        llSize = mRootView.findViewById(R.id.llSize);
        txtSize = mRootView.findViewById(R.id.txtSize);
        llWeight = mRootView.findViewById(R.id.llWeight);
        txtWeight = mRootView.findViewById(R.id.txtWeight);
        llDimension = mRootView.findViewById(R.id.llDimension);
        txtDimension = mRootView.findViewById(R.id.txtDimension);
        llProductStatus = mRootView.findViewById(R.id.llProductStatus);
        txtProductStatus = mRootView.findViewById(R.id.txtProductStatus);
        txtBuyPlace = mRootView.findViewById(R.id.txtBuyPlace);
        imgThumb = mRootView.findViewById(R.id.imgThumb);
        txtDescription = mRootView.findViewById(R.id.txtDescription);
        btnPrevious = mRootView.findViewById(R.id.btnPrevious);
        btnNext = mRootView.findViewById(R.id.btnNext);
        iconClock = mRootView.findViewById(R.id.icon_clock);
        iconNextArrow = mRootView.findViewById(R.id.icon_next_arrow);
        layoutUserInfo = mRootView.findViewById(R.id.layout_user_info);
        llCategoryParent = mRootView.findViewById(R.id.llCategoryParent);

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
//        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
//        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, mScreenHeight);
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth);
        scrollView.setHeaderLayoutParams(localObject);

//         load view from zoom view
        frameImage = mRootView.findViewById(R.id.frameImage);
        frameImage.setVisibility(View.VISIBLE);
        viewPager = mRootView.findViewById(R.id.viewPager);
        btnPrevious = mRootView.findViewById(R.id.btnPrevious);
        btnNext = mRootView.findViewById(R.id.btnNext);

//        fake data
//         set comment at here
        listComment = mRootView.findViewById(R.id.listComment);
//        List<ProductComment> productCommentList = new ArrayList<>();
//        productCommentList.add(new ProductComment("Khanh", "My first comment: Gấu Bông Chất Lượng Cao - Gấu Bông Chất Lượng Cao: Gấu Bông Chất Lượng Cao - Gấu Bông Chất Lượng Cao"));
//        productCommentList.add(new ProductComment("Khanh", "My first comment 2"));
//        productCommentList.add(new ProductComment("Khanh", "My first comment 3"));
//        ProductCommentAdapter commentAdapter = new ProductCommentAdapter(this, R.layout.product_comment_item, productCommentList);
//        listComment.setAdapter(commentAdapter);
////         This actually do the magic
//        listComment.setExpanded(true);

        layoutUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickUserInfo();
            }
        });

        txtExpandable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "txtExpandable clicked");
                if (!isExpand) {
                    txtExpandable.setText(getResources().getString(R.string.collapse));
                    dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_EXPAND_MAX);
                    dvDescription.requestLayout();
                    isExpand = true;
                    Log.d(LOG_TAG, "expand: line count = " + dvDescription.getLayout().getLineCount());
                } else {
                    txtExpandable.setText(getResources().getString(R.string.view_more));
                    dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_NO_EXPAND_MAX);
                    dvDescription.requestLayout();
                    isExpand = false;
                    Log.d(LOG_TAG, "un expand: line count = " + dvDescription.getLayout().getLineCount());
                }
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, ProductChatActivity.class);
                // put data before switch activity
                startActivity(intent);
            }
        });

    }

    private void clickUserInfo() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

    private void showCustomDialog() {
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
        dialog.findViewById(R.id.viewTop).setOnClickListener(closeListener);
        dialog.findViewById(R.id.btnCancel).setOnClickListener(closeListener);
        dialog.show();
    }

    @Override
    public void fetchData(final ProductDetailResponse response) {
        txtLike.setText(response.getLike().toString());
        txtComment.setText(response.getComment().toString());
        if (response.getIsLiked() == 0) {
            LoadImageUtils.loadImageFromDrawable(R.drawable.icon_like_off, imgLike);
        } else {
            LoadImageUtils.loadImageFromDrawable(R.drawable.icon_like_on, imgLike);
        }
        // name ?
        ProductDetailResponse.Seller seller = response.getSeller();
        txtName.setText(seller.getName());
        LoadImageUtils.loadImageFromUrl(seller.getAvatar(), R.drawable.unknown_user, imgAvatar, null);
        txtScore.setText(": " + response.getSeller().getScore());
        txtProduct.setText(": " + response.getSeller().getListing());
        dvDescription.setText(response.getDescribed());
        // format time ago
        txtTime.setText(Utils.formatTime(response.getCreated()));
        // load category...
        // load comment
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
        if (canEdit == 0) {
            btnBuy.setText(R.string.edit);
            btnBuy.setBackgroundResource(R.color.green_status);
        } else if (response.getIsBlocked() == 1) {
            btnBuy.setVisibility(View.GONE);
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
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
                    txtHeaderRunning.setText(response.getName());
                    txtHeaderRunning.setVisibility(View.VISIBLE);
                    txtHeaderRunning.setSelected(true);
                    txtHeader.setVisibility(View.GONE);
                } else {
                    txtHeaderRunning.setVisibility(View.GONE);
                }
            }
        });
        txtHeader.setText(response.getName());
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
        // fake data
        final List<String> imgList = urlList;
//        imgList.add(R.drawable.hm01);
//        imgList.add(R.drawable.hm02);
//        imgList.add(R.drawable.hm03);
//        imgList.add(R.drawable.hm04);
//        imgList.add(R.drawable.hm05);

        // not using fragment for view pager
//        viewPager.setAdapter(new ProductImagePagerAdapter(ProductDetailActivity.this, imgList));

        // using fragment for view pager
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
    public void setProductComment(List<ProductCommentResponse> commentList) {
        ProductCommentAdapter commentAdapter = new ProductCommentAdapter(this, R.layout.product_comment_item, commentList);
        listComment.setAdapter(commentAdapter);
//         This actually do the magic
        listComment.setExpanded(true);
    }

}