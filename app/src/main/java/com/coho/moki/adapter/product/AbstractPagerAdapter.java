package com.coho.moki.adapter.product;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Khanh Nguyen on 10/18/2017.
 */

public abstract class AbstractPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    public abstract void initOther();

}
