package com.coho.moki.adapter.product;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.model.ProductChatItem;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/22/2017.
 */

public class ProductChatAdapter extends RecyclerView.Adapter<ProductChatAdapter.ViewHolder> {

    private List<ProductChatItem> mProductChatItemList;

    public ProductChatAdapter(List<ProductChatItem> productChatItemList) {
        this.mProductChatItemList = productChatItemList;
    }

    @Override
    public ProductChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtCreated.setText("created at");
        holder.txtCreated.setVisibility(View.VISIBLE);
        holder.imgLeft.setImageResource(R.drawable.unknown_user);
        holder.imgRight.setImageResource(R.drawable.unknown_user);
        holder.txtMessage.setText("Message");
    }

    @Override
    public int getItemCount() {
        return mProductChatItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCreated;
        ImageView imgLeft;
        ImageView imgRight;
        TextView txtMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCreated = itemView.findViewById(R.id.txtCreated);
            imgLeft = itemView.findViewById(R.id.imgLeft);
            imgRight = itemView.findViewById(R.id.imgRight);
            txtMessage = itemView.findViewById(R.id.txtMessage);
        }

    }

}
