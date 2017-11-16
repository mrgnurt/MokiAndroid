package com.coho.moki.ui.user;

import android.widget.ListView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.FollowedAdapter;
import com.coho.moki.adapter.customadapter.FollowingAdapter;
import com.coho.moki.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/16/2017.
 */

public class UserFollowedFragment extends BaseFragment {

    public UserFollowedFragment() {}

    @BindView(R.id.list_view_user_follow)
    ListView mListViewUserFollowed;

    @Override
    protected int getLayoutId() {
        return R.layout.user_follow;
    }

    @Override
    protected void initView() {
        FollowedAdapter adapter = new FollowedAdapter(BaseApp.getContext(), R.layout.follow_user_item);
        mListViewUserFollowed.setAdapter(adapter);
        mListViewUserFollowed.setDivider(null);
    }



}
