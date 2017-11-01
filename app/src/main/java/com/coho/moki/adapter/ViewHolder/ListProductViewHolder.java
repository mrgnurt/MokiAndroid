package com.coho.moki.adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.model.Product;
import com.coho.moki.ui.custom.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trung on 16/10/2017.
 */

public class ListProductViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.first_image)
    ImageView mFirstImage;

    @BindView(R.id.name)
    TextView mTxtName;

    @BindView(R.id.like)
    TextView mTxtLike;

    @BindView(R.id.comment)
    TextView mTxtComment;

    @BindView(R.id.price)
    TextView mTxtPrice;

    public ListProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(Product product){
        mFirstImage.setImageResource(R.drawable.no_image);
        mTxtName.setText(product.getName());
        mTxtLike.setText(product.getNumLike() + "");
        mTxtComment.setText(product.getNumComment() + "");
        mTxtPrice.setText(product.getNumComment() + "");
    }
}
