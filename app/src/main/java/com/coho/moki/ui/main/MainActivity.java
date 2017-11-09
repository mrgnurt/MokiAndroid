package com.coho.moki.ui.main;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.SideMenuAdapter;
import com.coho.moki.callback.OnClickSideMenuItemListener;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.SideMenuItem;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.fragment.NewsPager.NewsPagerFragment;
import com.coho.moki.ui.main_search.MainSearchActivity;
import com.coho.moki.util.AccountUntil;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.coho.moki.ui.fragment.ProductPager.ProductPagerFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView{

    @Inject
    MainPresenter mMainPresenter;

    private Fragment currentDisplayFragment;
    private ProductPagerFragment productPagerFragment;
    private NewsPagerFragment mNewsPagerFragment;
    private int mCurrentMenuIndex = AppConstant.Home_MENU_INDEX;

//    @BindView(R.id.imgAvatar)
//    CircularImageView mImgAvatar;

//    @BindView(R.id.user_name)
    TextView mTxtUserName;

//    @BindView(R.id.side_menu_list)
    RecyclerView mRVSideMenu;

    @BindView(R.id.btnSearch)
    ImageButton mBtnSearch;

    @BindView(R.id.btnAlert)
    View mLLNotiCount;

    @BindView(R.id.layout_message_count)
    View mLLMessageCount;

    @BindView(R.id.btnSwitch)
    ImageButton mBtnSwitch;

    @BindView(R.id.btnMenu)
    ImageButton mBtnMenu;

    @BindView(R.id.iconAppName)
    View mIconAppName;

    @BindView(R.id.topbar)
    RelativeLayout mTopBar;

    @BindView(R.id.main_content)
    FrameLayout mMainContent;

    @BindView(R.id.main_layout_container)
    FrameLayout mMainLayoutContainer;

    @OnClick(R.id.btnMenu)
    public void onClickButtonMenu(){
        mSlidingMenu.toggle();
    }

    @OnClick(R.id.btnSearch)
    public void onClickButtonSearch(){
        Intent intent = new Intent(BaseApp.getContext(), MainSearchActivity.class);
        startActivity(intent);
    }

    SlidingMenu mSlidingMenu;

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        this.productPagerFragment = new ProductPagerFragment();
        mNewsPagerFragment = new NewsPagerFragment();
        initSlidingMenu();
        onMenuHomeSelect();
    }

    @Override
    public void initData() {

    }

    public void initSlidingMenu(){
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setBehindOffset(240);
        mSlidingMenu.setMenu(R.layout.side_menu);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        initMenu();
    }

    private void showUserName(){
        mTxtUserName.setText(AccountUntil.getUsername());
    }

    private void initMenu(){

        mTxtUserName = (TextView) findViewById(R.id.user_name);
        if (AccountUntil.getAccountId() != null){
            showUserName();
        }

        mRVSideMenu = (RecyclerView) findViewById(R.id.side_menu_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRVSideMenu.setLayoutManager(linearLayoutManager);

        List<SideMenuItem> sideMenuItems = SideMenuItem.getListSideMenuItem();

        SideMenuAdapter adapter = new SideMenuAdapter(sideMenuItems, this);
        mRVSideMenu.setAdapter(adapter);

        adapter.addListener(new OnClickSideMenuItemListener() {
            @Override
            public void onClick(int index) {
                setViewItemMenuSelect(index);
            }
        });
    }

    public void onMenuHomeSelect(){
        showTopButton();
        showFragment(productPagerFragment, "home");
    }

    private void showFragment(Fragment showFragment, String tag) {
//        if (showFragment != this.currentDisplayFragment) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
//                ft.add(R.id.main_content, showFragment, tag).addToBackStack(null);
//            } else {
//                ft.show(showFragment);
//            }
//            if (this.currentDisplayFragment != null) {
//                ft.hide(this.currentDisplayFragment);
//            }
//            ft.commit();
//            this.currentDisplayFragment = showFragment;
//        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_content, showFragment, tag).commit();
    }

    private void showTopButton() {
        this.mBtnSearch.setVisibility(View.VISIBLE);
        this.mLLNotiCount.setVisibility(View.VISIBLE);
        this.mLLMessageCount.setVisibility(View.VISIBLE);
        this.mBtnSwitch.setVisibility(View.VISIBLE);
        this.mBtnMenu.setVisibility(View.VISIBLE);
        this.mIconAppName.setVisibility(View.VISIBLE);
    }

    private void hideTopButton(){
        this.mBtnSearch.setVisibility(View.GONE);
        this.mLLNotiCount.setVisibility(View.GONE);
        this.mLLMessageCount.setVisibility(View.GONE);
        this.mBtnSwitch.setVisibility(View.GONE);
    }

    private void setViewItemMenuSelect(int index){
        mSlidingMenu.toggle();
        View v = mRVSideMenu.getLayoutManager().findViewByPosition(mCurrentMenuIndex);
        TextView textView = (TextView) v.findViewById(R.id.item_title);
        textView.setTextColor(Color.BLACK);
        mCurrentMenuIndex = index;
    }

    public void setVisibleTopBar(boolean visible, final View btnCamera){

        if (visible) {
            mMainLayoutContainer.animate()
                    .translationY(0)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(500);

//            LinearLayout.LayoutParams layoutParams =  new LinearLayout.LayoutParams(btnCamera.getWidth(), btnCamera.getHeight());
//            layoutParams.setMargins(0,0,0,100);
//            btnCamera.setLayoutParams(layoutParams);

            btnCamera.animate()
                    .translationY(-mTopBar.getHeight())
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(500);


        }
        else {
            mMainLayoutContainer.animate()
                    .translationY(-mTopBar.getHeight())
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(500)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            mMainLayoutContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2000));
//                            RelativeLayout.LayoutParams layoutParams =  new RelativeLayout.LayoutParams(btnCamera.getWidth(), btnCamera.getHeight());
//                            layoutParams.setMargin;
//                            btnCamera.setLayoutParams(layoutParams);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
//                            mMainLayoutContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

            btnCamera.animate()
                    .translationY(btnCamera.getHeight())
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(500);
        }
    }
}
