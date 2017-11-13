package com.coho.moki.adapter.customadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.ViewHolder.ListSizeViewHolder;
import com.coho.moki.callback.OnClickSizeItemListener;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.model.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 07/11/2017.
 */

public class ListSizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Size> mSizes;
    OnClickSizeItemListener mListener;

    public ListSizeAdapter(ArrayList<Size> sizes){
        mSizes = sizes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(BaseApp.getContext());

        View view = layoutInflater.inflate(R.layout.size_item, parent, false);

        ListSizeViewHolder viewHolder = new ListSizeViewHolder(view);
        viewHolder.setListener(mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListSizeViewHolder) holder).populate(mSizes.get(position));
    }

    @Override
    public int getItemCount() {
        return mSizes.size();
    }

    public void addListener(OnClickSizeItemListener listener){
        mListener = listener;
    }

    public void insertLastItem(List<Size> sizes){

        if (sizes != null && sizes.size() > 0){

            int positionStart = mSizes.size();
            mSizes.addAll(sizes);
            notifyItemRangeInserted(positionStart, sizes.size());
        }
    }
}
