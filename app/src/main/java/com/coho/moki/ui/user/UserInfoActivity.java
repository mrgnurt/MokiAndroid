package com.coho.moki.ui.user;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.UserInfoResponseData;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.coho.moki.util.network.LoadImageUtils;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.github.siyamed.shapeimageview.CircularImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.noties.scrollable.ScrollableLayout;

/**
 * Created by Khanh Nguyen on 10/14/2017.
 */

public class UserInfoActivity extends BaseActivity implements UserInfoView {

    private static final String TAG = "UserInfoActivity";
    private boolean isCollapse = true;

    private final int STATUS_MIN = 2;
    private final int STATUS_MAX = 60;
    private boolean firstSetShowHideButton = true;
    private String userId;
    private Integer numProduct;
    private Integer score;
    private boolean isFollowed = false;

    @Inject
    UserPresenter mUserPresenter;

    @BindView(R.id.bottomsheet)
    BottomSheetLayout bottomSheet;

    @BindView(R.id.user_info_layout)
    FrameLayout userInfoLayout;

    @BindView(R.id.scrollable_layout)
    ScrollableLayout scrollableLayout;

    @BindView(R.id.header)
    LinearLayout header;

    @BindView(R.id.imgBanner)
    ImageView imgBanner;

    @BindView(R.id.layoutAvatar)
    RelativeLayout layoutAvartar;

    @BindView(R.id.imgAvatar)
    CircularImageView imgAvatar;

    @BindView(R.id.img_state)
    ImageView imgState;

    @BindView(R.id.tv_user_state)
    TextView txtUserState;

    @BindView(R.id.txtProduct)
    TextView txtProduct;

    @BindView(R.id.txtScore)
    TextView txtScore;

    @BindView(R.id.btnFollow)
    ImageView btnFollow;

    @BindView(R.id.txtHappy)
    TextView txtHappy;

    @BindView(R.id.txtNormal)
    TextView txtNormal;

    @BindView(R.id.txtSad)
    TextView txtSad;

    @BindView(R.id.tv_view_all_rating)
    TextView txtViewAllRating;

    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @BindView(R.id.btnCollapse)
    Button btnCollapse;

//    @BindView(R.id.tabs)
//    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.btnCamera)
    Button btnCamera;

    @BindView(R.id.tutLayout)
    FrameLayout tutLayout;

    // bind view for navbar
    @BindView(R.id.btnNavLeft)
    ImageButton btnNavLeft;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.tab1)
    TextView tab1;

    @BindView(R.id.tab2)
    TextView tab2;

    @BindView(R.id.tab3)
    TextView tab3;

    @BindView(R.id.scroll_wrap_text)
    ScrollView scrollWrapText;

    @Override
    public int setContentViewId() {
        return R.layout.user_info;
    }

    @Override
    public void initView() {
        BaseApp.getActivityComponent().inject(this);
        mUserPresenter.onAttach(this);
        String token = AccountUntil.getUserToken();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        numProduct = intent.getIntExtra("numProduct", 0);
        score = intent.getIntExtra("score", 0);
        mUserPresenter.getUserInfoRemote(token, userId);
    }

    @Override
    public void initData() {

    }

    private void showHideMoreButton() {
        int lines = txtStatus.getLineCount();
        Log.d(TAG, "line count: " + lines);
        if (lines > 2) {
            btnCollapse.setVisibility(View.VISIBLE);
            txtStatus.setSingleLine(false);
            txtStatus.setEllipsize(TextUtils.TruncateAt.END);
            txtStatus.setMaxLines(STATUS_MIN);
        } else {
            btnCollapse.setVisibility(View.INVISIBLE);
        }
    }

    private void setTabsStatus(int position) {
        int selectedColor = Utils.getColorWrapper(UserInfoActivity.this, R.color.white);
        int unselectedColor = Utils.getColorWrapper(UserInfoActivity.this, R.color.black);
        switch (position) {
            case 0:
                tab1.setTextColor(selectedColor);
                tab1.setBackgroundResource(R.drawable.menu_user_info_selected1);
                tab2.setTextColor(unselectedColor);
                tab3.setTextColor(unselectedColor);
                tab2.setBackground(null);
                tab3.setBackground(null);
                break;
            case 1:
                tab2.setTextColor(selectedColor);
                tab2.setBackgroundResource(R.drawable.menu_user_info_selected2);
                tab3.setTextColor(unselectedColor);
                tab1.setTextColor(unselectedColor);
                tab3.setBackground(null);
                tab1.setBackground(null);
                break;
            case 2:
                tab3.setTextColor(selectedColor);
                tab3.setBackgroundResource(R.drawable.menu_user_info_selected3);
                tab1.setTextColor(unselectedColor);
                tab2.setTextColor(unselectedColor);
                tab1.setBackground(null);
                tab2.setBackground(null);
                break;
        }
    }

    @Override
    public void setUserInfo(UserInfoResponseData userInfoResponseData) {
        btnFollow.setVisibility(View.VISIBLE);
        txtHeader.setText(userInfoResponseData.getUsername());
        if (userInfoResponseData.getStatus() == "1") {
            imgState.setImageResource(R.drawable.icon_online);
            txtUserState.setText("Online");
        } else {
            imgState.setImageResource(R.drawable.icon_offline);
            txtUserState.setText("Offline");
        }

        txtHeader.setVisibility(View.VISIBLE);
        LoadImageUtils.loadImageFromUrl(userInfoResponseData.getAvatar(), R.drawable.unknown_user, imgAvatar, null);
        String htmlProduct = "<p><big><b>" + numProduct + "</b></big><br />Sản phẩm</p>";
        txtProduct.setText(Html.fromHtml(htmlProduct));
        String htmlScore = "<p><big><b>" + score + "</b></big><br />Điểm</p>";
        txtScore.setText(Html.fromHtml(htmlScore));
        txtHappy.setText("0");
        txtNormal.setText("0");
        txtSad.setText("0");

        if (userInfoResponseData.getFollowed() == 1) {
            isFollowed = true;
            btnFollow.setImageResource(R.drawable.bg_follow);
        } else {
            isFollowed = false;
            btnFollow.setImageResource(R.drawable.bg_unfollow);
        }

        btnCollapse.setVisibility(View.VISIBLE);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_all_rating));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtViewAllRating.setText(content);

        UserInfoFragmentAdapter userInfoFragmentAdapter = new UserInfoFragmentAdapter(this, getSupportFragmentManager(), userId);
        viewPager.setAdapter(userInfoFragmentAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabsStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabsClickListener tabsClickListener = new TabsClickListener();
        tab1.setOnClickListener(tabsClickListener);
        tab2.setOnClickListener(tabsClickListener);
        tab3.setOnClickListener(tabsClickListener);

        txtViewAllRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserRateActivity.class);
                startActivity(intent);
            }
        });

        StringBuilder status = new StringBuilder("Chưa có thông tin ");

        txtStatus.setText(status.toString());

        txtStatus.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int[] locations = new int[2];
                btnCollapse.getLocationInWindow(locations);
                scrollableLayout.setMaxScrollY(locations[1] - 60);
                if (firstSetShowHideButton) {
                    showHideMoreButton();
                    firstSetShowHideButton = false;
                }
            }
        });

        btnCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollapse) {
                    btnCollapse.setText(getResources().getString(R.string.view_more));
                    txtStatus.setMaxLines(STATUS_MIN);
                    isCollapse = false;
                } else {
                    btnCollapse.setText(getResources().getString(R.string.collapse));
                    txtStatus.setMaxLines(STATUS_MAX);
                    isCollapse = true;
                }
            }
        });

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showProgress(UserInfoActivity.this);
                if (isFollowed) {
                    DialogUtil.showPopupSuccess(UserInfoActivity.this, AppConstant.POPUP_UNFOLLOW);
                    isFollowed = false;
                    btnFollow.setImageResource(R.drawable.bg_unfollow);
                    DialogUtil.hideProgress();
                } else {
                    isFollowed = true;
                    btnFollow.setImageResource(R.drawable.bg_follow);
                    DialogUtil.hideProgress();
                }
            }
        });
    }

    private class TabsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = -1;
            switch (v.getId()) {
                case R.id.tab1:
                    position = 0;
                    btnCamera.setVisibility(View.VISIBLE);
                    break;
                case R.id.tab2:
                    position = 1;
                    btnCamera.setVisibility(View.INVISIBLE);
                    break;
                case R.id.tab3:
                    position = 2;
                    btnCamera.setVisibility(View.INVISIBLE);
                    break;
            }
            if (position != -1) {
                viewPager.setCurrentItem(position);
                setTabsStatus(position);
            }
        }

    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonNavLeft() {
        onBackPressed();
    }

}
