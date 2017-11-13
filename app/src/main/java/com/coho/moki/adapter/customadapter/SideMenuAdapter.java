package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coho.moki.R;
import com.coho.moki.adapter.ViewHolder.SideMenuViewHolder;
import com.coho.moki.callback.OnClickSideMenuItemListener;
import com.coho.moki.data.constant.SideMenuItem;

import java.util.List;

/**
 * Created by trung on 11/10/2017.
 */

public class SideMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SideMenuItem> mSideMenuItems;
    private Context mContext;
    private OnClickSideMenuItemListener mListener;

    public SideMenuAdapter(List<SideMenuItem> mSideMenuItems, Context mContext) {
        this.mSideMenuItems = mSideMenuItems;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.side_menu_item, parent, false);
        SideMenuViewHolder sideMenuViewHolder = new SideMenuViewHolder(view);
        sideMenuViewHolder.setListener(mListener);

        return sideMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SideMenuViewHolder)holder).populate(mSideMenuItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mSideMenuItems.size();
    }

    public void addListener(OnClickSideMenuItemListener listener){
        mListener = listener;
    }
}
