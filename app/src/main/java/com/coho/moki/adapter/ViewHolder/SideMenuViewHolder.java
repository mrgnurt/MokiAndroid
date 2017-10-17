package com.coho.moki.adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.constant.SideMenuItem;

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

    public SideMenuViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(SideMenuItem sideMenuItem){
        mImgItemIcon.setImageResource(sideMenuItem.getIcon());
        mTVItemTitle.setText(sideMenuItem.getTitle());
    }
}
