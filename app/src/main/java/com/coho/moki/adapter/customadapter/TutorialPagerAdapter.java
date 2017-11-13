package com.coho.moki.adapter.customadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.coho.moki.ui.fragment.TutorialFragment1;
import com.coho.moki.ui.fragment.TutorialFragment2;
import com.coho.moki.ui.fragment.TutorialFragment3;

/**
 * Created by trung on 10/11/2017.
 */

public class TutorialPagerAdapter extends FragmentPagerAdapter {

//    Fragment fragment1;
//    Fragment fragment2;
//    Fragment fragment3;

    public TutorialPagerAdapter(FragmentManager fm) {
        super(fm);
//        fragment1 = new TutorialFragment1();
//        fragment2 = new TutorialFragment2();
//        fragment3 = new TutorialFragment3();
    }

    @Override
    public Fragment getItem(int position) {

        Log.d("getItem", position + "");
        if (position == 0){
            return new TutorialFragment1();
        }
        else if (position == 1) {
            return new TutorialFragment2();
        }
        else {
            return new TutorialFragment3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
