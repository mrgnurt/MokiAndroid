package com.coho.moki.ui.user;

import android.widget.ListView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.FollowingAdapter;
import com.coho.moki.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/16/2017.
 */

public class UserFollowingFragment extends BaseFragment{

    @BindView(R.id.list_view_user_follow)
    ListView mListViewUserFollowing;

    public UserFollowingFragment() {}

    @Override
    protected int getLayoutId() {
        return R.layout.user_follow;
    }

    @Override
    protected void initView() {
        FollowingAdapter adapter = new FollowingAdapter(BaseApp.getContext(), R.layout.follow_user_item);
        mListViewUserFollowing.setAdapter(adapter);
        mListViewUserFollowing.setDivider(null);
    }
}
