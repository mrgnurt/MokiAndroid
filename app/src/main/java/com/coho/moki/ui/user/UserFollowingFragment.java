package com.coho.moki.ui.user;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coho.moki.R;

/**
 * Created by Khanh Nguyen on 10/16/2017.
 */

public class UserFollowingFragment extends Fragment{

    public UserFollowingFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_following_fragment, container, false);
        return view;
    }

}
