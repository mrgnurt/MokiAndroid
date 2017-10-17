package com.coho.moki.ui.main;

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

import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.SideMenuAdapter;
import com.coho.moki.data.constant.SideMenuItem;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.fragment.ProductPagerFragment;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView{

    private Fragment currentDisplayFragment;
    private ProductPagerFragment productPagerFragment;

//    @BindView(R.id.imgAvatar)
//    CircularImageView mImgAvatar;
//
//    @BindView(R.id.side_menu_list)
//    RecyclerView mRVSideMenu;

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
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        this.productPagerFragment = new ProductPagerFragment();
//        initRv();
        onMenuHomeSelect();
    }

    @Override
    public void initData() {

    }

//    public void initRv(){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRVSideMenu.setLayoutManager(linearLayoutManager);
//
//        List<SideMenuItem> sideMenuItems = SideMenuItem.getListSideMenuItem();
//
//        SideMenuAdapter adapter = new SideMenuAdapter(sideMenuItems, this);
//        mRVSideMenu.setAdapter(adapter);
//    }

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
}
