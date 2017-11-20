package com.coho.moki.adapter.ViewHolder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.callback.OnClickSideMenuItemListener;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.SideMenuItem;
import com.coho.moki.util.AccountUntil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trung on 11/10/2017.
 */

public class SideMenuViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_icon)
    ImageView mImgItemIcon;

    @BindView(R.id.item_title)
    TextView mTVItemTitle;

    @BindView(R.id.ll_menu_item)
    LinearLayout mLLMenuItem;

    OnClickSideMenuItemListener mListener;
    SideMenuItem mSideMenuItem;

    public SideMenuViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        initListener();
    }

    public void populate(SideMenuItem sideMenuItem){
        mSideMenuItem = sideMenuItem;

        if (sideMenuItem.getIndex() == 9){
            mImgItemIcon.setImageResource(sideMenuItem.getIcon());


            if (AccountUntil.getUserToken() != null){
                mTVItemTitle.setText(BaseApp.getContext().getResources().getText(R.string.sidemenu_title_logout).toString());
            }
            else {
                mTVItemTitle.setText(BaseApp.getContext().getResources().getText(R.string.sidemenu_icon_login).toString());
            }
            return;
        }

        mImgItemIcon.setImageResource(sideMenuItem.getIcon());
        mTVItemTitle.setText(sideMenuItem.getTitle());
        if (sideMenuItem.getIndex() == AppConstant.Home_MENU_INDEX){
            mTVItemTitle.setTextColor(Color.RED);
        }
    }

    public void setListener(OnClickSideMenuItemListener listener){
        mListener = listener;
    }

    private void initListener(){
        mLLMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTVItemTitle.setTextColor(Color.RED);
                mListener.onClick(mSideMenuItem.getIndex());
            }
        });
    }
}
