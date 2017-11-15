package com.coho.moki.ui.user;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.FollowedAdapter;
import com.coho.moki.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/16/2017.
 */

public class UserFollowingFragment extends BaseFragment{

    @BindView(R.id.list_view_user_follow)
    ListView mListViewUserFollow;

    public UserFollowingFragment() {}

    @Override
    protected int getLayoutId() {
        return R.layout.user_follow;
    }

    @Override
    protected void initView() {

        FollowedAdapter adapter = new FollowedAdapter(BaseApp.getContext(), R.layout.follow_user_item);
        mListViewUserFollow.setAdapter(adapter);
    }
}
