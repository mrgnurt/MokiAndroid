package com.coho.moki.adapter.customadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.coho.moki.data.model.Category;
import com.coho.moki.ui.fragment.ListProduct.ListProductFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 14/10/2017.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    List<Category> mCategories;
    SparseArray<ListProductFragment> registeredFragments = new SparseArray<ListProductFragment>();

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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ListProductFragment fragment = (ListProductFragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public ListProductFragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }


}
