package com.coho.moki.ui.user;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.FollowingAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.UserFollowResponseData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.service.UserService;
import com.coho.moki.service.UserServiceImpl;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/16/2017.
 */

public class UserFollowingFragment extends BaseFragment{

    @BindView(R.id.list_view_user_follow)
    ListView mListViewUserFollowing;
    ArrayList<UserFollowResponseData> userFollowingList;
    String userId;
    UserService userService = new UserServiceImpl();

    public UserFollowingFragment(String id) {
        userId = id;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_follow;
    }

    @Override
    protected void initView() {
        String token = AccountUntil.getUserToken();
        Integer index = 0;
        Integer count = AppConstant.COUNT_USER_FOLLOW_GET;
        getUserFollowing(token, userId, index, count);
        mListViewUserFollowing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String userId = userFollowingList.get(i).getId();
                clickUserInfo(userId, 0, 0);
            }
        });
    }

    public void getUserFollowing(String token, String userId, Integer index, Integer count) {
        userService.getUserFollow(token, userId, index, count, AppConstant.FOLLOWING,
                new ResponseListener<ArrayList<UserFollowResponseData>>() {

                    @Override
                    public void onSuccess(ArrayList<UserFollowResponseData> dataResponse) {
                        userFollowingList = dataResponse;
                        FollowingAdapter adapter = new FollowingAdapter(getActivity(), R.layout.follow_user_item, userFollowingList);
                        mListViewUserFollowing.setAdapter(adapter);
                        mListViewUserFollowing.setDivider(null);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        userFollowingList = new ArrayList<UserFollowResponseData>();
                        Utils.toastShort(BaseApp.getContext(), errorMessage);
                        FollowingAdapter adapter = new FollowingAdapter(getActivity(), R.layout.follow_user_item, userFollowingList);
                        mListViewUserFollowing.setAdapter(adapter);
                        mListViewUserFollowing.setDivider(null);
                    }
        });
    }

    private void clickUserInfo(String userId, Integer numProduct, Integer score) {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        DialogUtil.showProgress(getActivity());
        intent.putExtra("userId", userId);
        intent.putExtra("numProduct", numProduct);
        intent.putExtra("score", score);
        startActivity(intent);
    }
}
