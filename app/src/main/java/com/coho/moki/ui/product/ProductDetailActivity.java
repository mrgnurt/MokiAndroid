package com.coho.moki.ui.product;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.coho.moki.adapter.product.ProductCommentAdapter;
import com.coho.moki.adapter.product.ProductImagePagerAdapter;
import com.coho.moki.data.model.ProductComment;
import com.coho.moki.ui.base.BaseActivity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coho.moki.ui.user.UserInfoActivity;
import com.coho.moki.util.Utils;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.coho.moki.R;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;


/**
 * Created by Khanh Nguyen on 10/12/2017.
 */

public class ProductDetailActivity extends BaseActivity implements ProductDetailView {

    private static final String LOG_TAG = ProductDetailActivity.class.getSimpleName();
    private boolean isExpand = false;
    private static final Integer DESCRIPTON_NO_EXPAND_MAX = 2;
    private static final Integer DESCRIPTION_EXPAND_MAX = 40;

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

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtHeaderRunning)
    TextView txtHeaderRunning;

    @Override
    public int setContentViewId() {
        return R.layout.product_detail;
    }

    @Override
    public void initView() {
        loadViewForCode();

//        ActionBar mActionBar = getSupportActionBar();  //to support lower version too
//        mActionBar.setDisplayShowHomeEnabled(false);
//        mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        View mCustomView = mInflater.inflate(R.layout.navigation_bar, null);
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);

    }

    @Override
    public void initData() {
        initFakeData();
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
        dvDescription.setText("Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao - Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao - " +
                "Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao - Gấu Bông Sỉ Hàn Quốc Chất Lượng Cao");
        dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTON_NO_EXPAND_MAX);
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
        txtProductStatus = mRootView.findViewById(R.id.txtProductStatus);
        txtBuyPlace = mRootView.findViewById(R.id.txtBuyPlace);
        imgThumb = mRootView.findViewById(R.id.imgThumb);
        txtDescription = mRootView.findViewById(R.id.txtDescription);
        btnPrevious = mRootView.findViewById(R.id.btnPrevious);
        btnNext = mRootView.findViewById(R.id.btnNext);
        iconClock = mRootView.findViewById(R.id.icon_clock);
        iconNextArrow = mRootView.findViewById(R.id.icon_next_arrow);

        layoutUserInfo = mRootView.findViewById(R.id.layout_user_info);

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

        // fake data
        final List<Integer> imgList = new ArrayList<>();
        imgList.add(R.drawable.hm);
        imgList.add(R.drawable.hm01);
        imgList.add(R.drawable.hm02);
        viewPager.setAdapter(new ProductImagePagerAdapter(ProductDetailActivity.this, imgList));

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

//        fake data
//         set comment at here
        listComment = mRootView.findViewById(R.id.listComment);
        List<ProductComment> productCommentList = new ArrayList<>();
        productCommentList.add(new ProductComment("Khanh", "My first comment"));
        productCommentList.add(new ProductComment("Khanh", "My first comment 2"));
        productCommentList.add(new ProductComment("Khanh", "My first comment 3"));
        ProductCommentAdapter commentAdapter = new ProductCommentAdapter(this, R.layout.product_comment_item, productCommentList);
        listComment.setAdapter(commentAdapter);
//         This actually do the magic
        listComment.setExpanded(true);

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
                    dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTION_EXPAND_MAX);
                    dvDescription.requestLayout();
                    isExpand = true;
                } else {
                    dvDescription.getDocumentLayoutParams().setMaxLines(DESCRIPTON_NO_EXPAND_MAX);
                    dvDescription.requestLayout();
                    isExpand = false;
                }
            }
        });
    }

    private void clickUserInfo() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

}
