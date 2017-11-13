package com.coho.moki.ui.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.product.MediaActivity;
import com.coho.moki.ui.product.UserRateActivity;
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
    private boolean isFirstCall = true;
    private boolean isCollapse = true;

    // not effect?
    private int maxScroll = 272; // default height + 2 line = 252, + button height = 268, + line = ?
    private int maxScrollButtonCollapse = maxScroll + 16;
    private int heightStatusCollapse = 0;
    private int defaultStatusHeight = 0;


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

        StringBuilder status = new StringBuilder("Gấu bông chất lượng cao Hà Nội - ");

        for (int i = 0; i < 5; ++i) {
            status.append(status);
        }

        txtStatus.setText(status.toString());

//         set text for txtStatus before, if number line of status > 2

//        txtStatus.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                Log.d(LOG_TAG, "on layout change listener: " + getTextViewHeight(txtStatus));
//                int currentLine = txtStatus.getLayout().getLineCount();
//                if (currentLine <= 2) {
//                    btnCollapse.setVisibility(View.GONE);
//                    scrollableLayout.setMaxScrollY(maxScroll);
//                    scrollableLayout.requestLayout();
//                } else {
//                    btnCollapse.setVisibility(View.VISIBLE);
//                    btnCollapse.setText(getResources().getString(R.string.view_more));
//                    scrollableLayout.setMaxScrollY(maxScrollButtonCollapse);
//                    scrollableLayout.requestLayout();
//                    txtStatus.setMaxLines(10);
//                    txtStatus.requestLayout();
//                    defaultStatusHeight = txtStatus.getMeasuredHeight();
//                    Log.d(LOG_TAG, "defaultStatusHeight = " + defaultStatusHeight);
//                    txtStatus.setMaxLines(2);
//                    txtStatus.requestLayout();
//                    heightStatusCollapse = txtStatus.getLayout().getHeight();
//                    Log.d(LOG_TAG, "heightStatusCollapse = " + heightStatusCollapse);
//                }
//                isFirstCall = false;
//            }
//        });

        txtStatus.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(LOG_TAG, "global layout change listener: " + getTextViewHeight(txtStatus));
                if (isFirstCall) {
                    int currentLine = txtStatus.getLayout().getLineCount();
                    if (currentLine <= 2) {
                        btnCollapse.setVisibility(View.GONE);
                        scrollableLayout.setMaxScrollY(maxScroll);
                        scrollableLayout.requestLayout();
                    } else {
                        btnCollapse.setVisibility(View.VISIBLE);
                        btnCollapse.setText(getResources().getString(R.string.view_more));
                        scrollableLayout.setMaxScrollY(maxScrollButtonCollapse);
                        scrollableLayout.requestLayout();
                        txtStatus.setMaxLines(10);
                        txtStatus.requestLayout();
                        defaultStatusHeight = txtStatus.getMeasuredHeight();
                        Log.d(LOG_TAG, "defaultStatusHeight = " + defaultStatusHeight);
                        txtStatus.setMaxLines(2);
                        txtStatus.requestLayout();
                        heightStatusCollapse = txtStatus.getLayout().getHeight();
                        Log.d(LOG_TAG, "heightStatusCollapse = " + heightStatusCollapse);
                    }
                    isFirstCall = false;
                }
            }
        });

        btnCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollapse) {
                    btnCollapse.setText(getResources().getString(R.string.view_more));
                    txtStatus.setMaxLines(2);
                    isCollapse = false;
                    scrollableLayout.setMaxScrollY(maxScrollButtonCollapse);
                    scrollableLayout.requestLayout();
                } else {
                    btnCollapse.setText(getResources().getString(R.string.collapse));
                    txtStatus.setMaxLines(10);
                    isCollapse = true;
                    scrollableLayout.setMaxScrollY(maxScrollButtonCollapse + defaultStatusHeight - heightStatusCollapse);
                    scrollableLayout.requestLayout();

                    Log.d(LOG_TAG, "global layout change listener collapse false: " + getTextViewHeight(txtStatus));
                }
            }
        });

//        txtStatus.setMovementMethod(new ScrollingMovementMethod());

//        scrollWrapText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                txtStatus.getParent().requestDisallowInterceptTouchEvent(false);
//                return false;
//            }
//        });
//
//        txtStatus.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                txtStatus.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });

    }

    /**
     * Get the TextView height before the TextView will render
     * @param textView the TextView to measure
     * @return the height of the textView
     */
    public  int getTextViewHeight(TextView textView) {
        WindowManager wm =
                (WindowManager) textView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int deviceWidth;

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
            Point size = new Point();
            display.getSize(size);
            deviceWidth = size.x;
        } else {
            deviceWidth = display.getWidth();
        }

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
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
