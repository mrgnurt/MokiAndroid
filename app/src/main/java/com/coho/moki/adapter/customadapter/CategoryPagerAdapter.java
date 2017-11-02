package com.coho.moki.adapter.customadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coho.moki.data.model.Category;
import com.coho.moki.ui.fragment.ListProduct.ListProductFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 14/10/2017.
 */

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    List<Category> mCategories;

    public CategoryPagerAdapter(FragmentManager fm, List<Category> categories) {
        super(fm);
        mCategories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        return ListProductFragment.newInstance(mCategories.get(position));
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategories.get(position).getCategoryName();
    }
}
