package com.coho.moki.ui.main;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.SideMenuAdapter;
import com.coho.moki.callback.OnClickSideMenuItemListener;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.SideMenuItem;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.fragment.NewsPager.NewsPagerFragment;
import com.coho.moki.ui.fragment.ProductPagerFragment;
import com.coho.moki.util.AccountUntil;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView{

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

    @OnClick(R.id.btnMenu)
    public void onClickButtonMenu(){
        mSlidingMenu.toggle();
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
        fragmentTransaction.add(R.id.main_content, showFragment).commit();
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
}
