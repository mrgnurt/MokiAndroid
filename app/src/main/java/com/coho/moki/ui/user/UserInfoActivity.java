package com.coho.moki.ui.user;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.github.siyamed.shapeimageview.CircularImageView;

import butterknife.BindView;
import ru.noties.scrollable.ScrollableLayout;

/**
 * Created by Khanh Nguyen on 10/14/2017.
 */

public class UserInfoActivity extends BaseActivity implements UserInfoView {

    private static final String LOG_TAG = UserInfoActivity.class.getSimpleName();

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


    @Override
    public int setContentViewId() {
        return R.layout.user_info;
    }

    @Override
    public void initView() {
        initFakeView();
        initFakeData();
    }

    @Override
    public void initData() {

    }

    private void initFakeView() {
        btnFollow.setVisibility(View.VISIBLE);
//        btnCollapse.setVisibility(View.VISIBLE);
//        btnNavRight.setScaleType(ImageView.ScaleType.FIT_XY);
        //btnNavRight.setImageResource(R.drawable.icon_share_product);

        txtHeader.setText("Jake Wharton");
        txtHeader.setVisibility(View.VISIBLE);
        String htmlProduct = "<p><big><b>5</b></big><br />Sản phẩm</p>";
        txtProduct.setText(Html.fromHtml(htmlProduct));
        String htmlScore = "<p><big><b>10</b></big><br />Điểm</p>";
        txtScore.setText(Html.fromHtml(htmlScore));
        txtHappy.setText("1");
        txtNormal.setText("2");
        txtSad.setText("3");

        btnCollapse.setVisibility(View.VISIBLE);

        SpannableString content = new SpannableString(getResources().getString(R.string.view_all_rating));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtViewAllRating.setText(content);

        btnNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        UserInfoFragmentAdapter userInfoFragmentAdapter = new UserInfoFragmentAdapter(this, getSupportFragmentManager());
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

        tab1.setOnClickListener(new TabsClickListener());
        tab2.setOnClickListener(new TabsClickListener());
        tab3.setOnClickListener(new TabsClickListener());
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

    private void initFakeData() {

    }

    private class TabsClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position = -1;
            switch (v.getId()) {
                case R.id.tab1:
                    position = 0;
                    break;
                case R.id.tab2:
                    position = 1;
                    break;
                case R.id.tab3:
                    position = 2;
                    break;
            }
            if (position != -1) {
                viewPager.setCurrentItem(position);
                setTabsStatus(position);
            }
        }

    }

}
