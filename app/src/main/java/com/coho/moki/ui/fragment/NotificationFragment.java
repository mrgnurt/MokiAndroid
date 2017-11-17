package com.coho.moki.ui.fragment;

import android.graphics.Color;
import android.widget.Button;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by trung on 17/11/2017.
 */

public class NotificationFragment extends BaseFragment {

    @BindView(R.id.btAction)
    Button mBtAction;

    @BindView(R.id.btNoti)
    Button mBtNoti;

    @OnClick(R.id.btAction)
    public void onClickButtonAction(){
        mBtAction.setBackgroundResource(R.drawable.backgroud_radius_top_left);
        mBtAction.setTextColor(getResources().getColor(R.color.primary));

        mBtNoti.setBackgroundColor(Color.TRANSPARENT);
        mBtNoti.setTextColor(getResources().getColor(R.color.secondary_text));
    }

    @OnClick(R.id.btNoti)
    public void onClickNuttonNoti(){
        mBtNoti.setBackgroundResource(R.drawable.backgroud_radius_top_left);
        mBtNoti.setTextColor(getResources().getColor(R.color.primary));

        mBtAction.setBackgroundColor(Color.TRANSPARENT);
        mBtAction.setTextColor(getResources().getColor(R.color.secondary_text));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void initView() {

    }
}
