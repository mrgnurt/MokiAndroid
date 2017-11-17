package com.coho.moki.ui.user;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.FollowedAdapter;
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

public class UserFollowedFragment extends BaseFragment{

    @BindView(R.id.list_view_user_follow)
    ListView mListViewUserFollowed;
    ArrayList<UserFollowResponseData> userFollowedList;
    String userId;
    UserService userService = new UserServiceImpl();

    public UserFollowedFragment(String id) {
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
        getUserFollowed(token, userId, index, count);
        mListViewUserFollowed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String userId = userFollowedList.get(i).getId();
                clickUserInfo(userId, 0, 0);
            }
        });
    }

    public void getUserFollowed(String token, String userId, Integer index, Integer count) {
        userService.getUserFollow(token, userId, index, count, AppConstant.FOLLOWED,
                new ResponseListener<ArrayList<UserFollowResponseData>>() {

                    @Override
                    public void onSuccess(ArrayList<UserFollowResponseData> dataResponse) {
                        userFollowedList = dataResponse;
                        FollowedAdapter adapter = new FollowedAdapter(BaseApp.getContext(), R.layout.follow_user_item, userFollowedList);
                        mListViewUserFollowed.setAdapter(adapter);
                        mListViewUserFollowed.setDivider(null);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        userFollowedList = new ArrayList<UserFollowResponseData>();
                        Utils.toastShort(BaseApp.getContext(), errorMessage);
                        FollowedAdapter adapter = new FollowedAdapter(BaseApp.getContext(), R.layout.follow_user_item, userFollowedList);
                        mListViewUserFollowed.setAdapter(adapter);
                        mListViewUserFollowed.setDivider(null);
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
