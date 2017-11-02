package com.coho.moki.ui.fragment.ProductPager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.CategoryPagerAdapter;
import com.coho.moki.data.model.Category;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.main.MainActivity;

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
        Log.d("trung", "abc");
        mPresenter = new ProductPagerPresenter();
        mPresenter.onAttach(this);
    }

    @Override
    protected void initView() {
        super.initView();
        initTabLayout();
        showBannerSlider();
        mScrollableLayout.addOnScrollChangedListener(onScrollChangedListener);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    public void initTabLayout(){
        categories.add(new Category("", "Tat ca"));
        categories.add(new Category("59e96abffe038830efc1a71e", "Mien phi"));
        categories.add(new Category("", "Be an"));
        categories.add(new Category("2", "Be an"));
        categories.add(new Category("3", "Be an"));
        categories.add(new Category("4", "Be an"));
        categories.add(new Category("5", "Be an"));
        categories.add(new Category("6", "Be an"));
        categories.add(new Category("7", "Be an"));
        CategoryPagerAdapter adapter = new CategoryPagerAdapter(getFragmentManager(), categories);
        mVPContent.setAdapter(adapter);
        mTLCategories.setupWithViewPager(mVPContent);
    }

    private OnScrollChangedListener onScrollChangedListener = new OnScrollChangedListener() {
        @Override
        public void onScrollChanged(int y, int oldY, int maxY) {
            if (y == maxY) {
                setVisibleScrollableLayout(false);
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
            mBtnCamera.setVisibility(View.VISIBLE);
//            ((MainActivity)getActivity()).setVisibleTopBar(visible);
        }
        else {
            mBtnCamera.setVisibility(View.INVISIBLE);
//            ((MainActivity)getActivity()).setVisibleTopBar(visible);
        }
    }
}
