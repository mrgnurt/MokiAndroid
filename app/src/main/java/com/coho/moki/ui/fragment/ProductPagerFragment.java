package com.coho.moki.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.CategoryPagerAdapter;
import com.coho.moki.data.model.Category;
import com.coho.moki.ui.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.OverScrollListener;
import ru.noties.scrollable.ScrollableLayout;

/**
 * Created by trung on 11/10/2017.
 */

public class ProductPagerFragment extends BaseFragment {

    @BindView(R.id.tl_categories)
    TabLayout mTLCategories;

    @BindView(R.id.content_viewpager)
    ViewPager mVPContent;

    @BindView(R.id.scrollable_layout)
    ScrollableLayout mScrollableLayout;

    @BindView(R.id.campaign_view_container)
    View mCampaignViewContainer;

    ArrayList<Category> categories = new ArrayList<Category>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        super.initView();
        initTabLayout();

        mScrollableLayout.addOnScrollChangedListener(onScrollChangedListener);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    public void initTabLayout(){
        categories.add(new Category("0", "Tat ca"));
        categories.add(new Category("0", "Mien phi"));
        categories.add(new Category("1", "Be an"));
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
                mScrollableLayout.setSelfUpdateScroll(true);
            }
        }
    };
}
