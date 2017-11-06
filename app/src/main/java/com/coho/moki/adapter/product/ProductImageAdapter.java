package com.coho.moki.adapter.product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.coho.moki.ui.fragment.ProductImageFragment;

import java.util.List;

/**
 * Created by Khanh Nguyen on 10/31/2017.
 */

public class ProductImageAdapter extends FragmentStatePagerAdapter {

    private List<String> imgList;

    public ProductImageAdapter(FragmentManager fm, List<String> imgList) {
        super(fm);
        this.imgList = imgList;
    }

    @Override
    public Fragment getItem(int position) {
        return ProductImageFragment.newInstance(imgList , position);
    }

    @Override
    public int getCount() {
        return imgList.size();
    }
}
