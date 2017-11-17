package com.coho.moki.ui.user;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by Khanh Nguyen on 10/16/2017.
 */

public class UserInfoFragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    private String userId = "";

    public UserInfoFragmentAdapter(Context context, FragmentManager fm, String id) {
        super(fm);
        userId = id;
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment =  new UserProductFragment(userId);
                break;
            case 1:
                fragment = new UserFollowingFragment(userId);
                break;
            case 2:
                fragment = new UserFollowedFragment(userId);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
