package com.coho.moki.ui.fragment.ProductPager;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Camera;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.CategoryPagerAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Category;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.fragment.ListProduct.ListProductFragment;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.ui.product.CameraActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by trung on 11/10/2017.
 */

public class ProductPagerFragment extends BaseFragment implements ProductPagerContract.View {

    @BindView(R.id.tl_categories)
    TabLayout mTLCategories;

    @BindView(R.id.content_viewpager)
    ViewPager mVPContent;

    @BindView(R.id.scrollable_layout)
    ScrollableLayout mScrollableLayout;

    @BindView(R.id.campaign_view_container)
    View mCampaignViewContainer;

    @BindView(R.id.banner_slider)
    BannerSlider mBannerSlider;

    @BindView(R.id.btnCamera)
    Button mBtnCamera;

    ProductPagerContract.Presenter mPresenter;
    CategoryPagerAdapter mCategoryPagerAdapter;

    List<Banner> banners = new ArrayList<Banner>();
    List<Category> categories = new ArrayList<Category>();

    public static ProductPagerFragment newInstance(){
        ProductPagerFragment productPagerFragment = new ProductPagerFragment();
        return productPagerFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new ProductPagerPresenter();
        mPresenter.onAttach(this);
    }

    @Override
    protected void initView() {
        super.initView();
        initTabLayout();
        showBannerSlider();
        mPresenter.callServiceGetCampaigns();
//        mScrollableLayout.addOnScrollChangedListener(onScrollChangedListener);

        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CameraActivity.class));
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();

    }

    public void initTabLayout(){
        categories.add(new Category("", "Tất cả"));
        categories.add(new Category("", "Miễn phí"));
        categories.add(new Category("", "Bé ăn"));
        categories.add(new Category("", "Bé mặc"));
        categories.add(new Category("", "Bé ngủ"));
        categories.add(new Category("", "Bé tắm"));
        categories.add(new Category("", "Bé vệ sinh"));
        categories.add(new Category("", "Bé khỏe - an toàn"));
        categories.add(new Category("", "Bé đi ra ngoài"));
        mCategoryPagerAdapter = new CategoryPagerAdapter(getFragmentManager(), categories);
        mVPContent.setAdapter(mCategoryPagerAdapter);
        mTLCategories.setupWithViewPager(mVPContent);
    }

    private OnScrollChangedListener onScrollChangedListener = new OnScrollChangedListener() {
        @Override
        public void onScrollChanged(int y, int oldY, int maxY) {
            if (y == maxY) {
//                setVisibleScrollableLayout(false);
            }
        }
    };


    public void showBannerSlider(){
        banners.add(new DrawableBanner(R.drawable.image1));
        banners.add(new DrawableBanner(R.drawable.image2));

        mBannerSlider.setBanners(banners);
    }

    @Override
    public void setVisibleScrollableLayout(boolean visible) {
        mScrollableLayout.setSelfUpdateScroll(!visible);
    }

    @Override
    public void setVisibleButtonCamera(boolean visible) {

        if (visible) {
            ((MainActivity)getActivity()).setVisibleTopBar(visible, mBtnCamera);
        }
        else {
            ((MainActivity)getActivity()).setVisibleTopBar(visible, mBtnCamera);
        }
    }

    public void changeListProductLayout(){

        if (mVPContent.getAdapter() != null && mVPContent.getAdapter().getCount() != 0){
            for (int i = 0; i < mVPContent.getAdapter().getCount(); i++){
                ListProductFragment fragment = (ListProductFragment) mCategoryPagerAdapter.getRegisteredFragment(i);
                if (fragment != null){
                    fragment.changeViewMode();
                }
            }
        }
    }

}
