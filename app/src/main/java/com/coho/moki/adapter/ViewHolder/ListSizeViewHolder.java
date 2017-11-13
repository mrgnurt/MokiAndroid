package com.coho.moki.adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.callback.OnClickSizeItemListener;
import com.coho.moki.data.model.Size;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trung on 07/11/2017.
 */

public class ListSizeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtTextSize)
    TextView mTxtTextSize;

    @BindView(R.id.rl_size_item)
    RelativeLayout mRLSizeItem;

    OnClickSizeItemListener mListener;
    Size mSize;

    public ListSizeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        initListener();
    }

    public void populate(Size size){
        mSize = size;
        Log.d("size", size.getSizeName());
        mTxtTextSize.setText(size.getSizeName());
    }

    public void setListener(OnClickSizeItemListener listener){
        mListener = listener;
    }

    private void initListener(){
        mRLSizeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(mSize);
            }
        });
    }
}
