package com.coho.moki.adapter.product;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.model.ProductChatItem;
import com.coho.moki.util.network.LoadImageUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/22/2017.
 */

public class ProductChatAdapter extends RecyclerView.Adapter<ProductChatAdapter.ViewHolder> {

    public List<ProductChatItem> chatList;


    public ProductChatAdapter(List<ProductChatItem> productChatItemList) {
        this.chatList = productChatItemList;
    }

    @Override
    public ProductChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductChatItem item = chatList.get(position);
        holder.populate(item);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void addItem(ProductChatItem item) {
        if (item == null) {
            return;
        }

        chatList.add(item);
        this.notifyItemInserted(getItemCount() - 1);
    }

    public void addItems(List<ProductChatItem> addItems) {
        if (addItems == null || addItems.size() < 1) {
            return;
        }

        int posStartInsert = getItemCount();
        chatList.addAll(addItems);
        this.notifyItemRangeInserted(posStartInsert, addItems.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCreated;
        ImageView imgLeft;
        ImageView imgRight;
        TextView txtMessage;

        String unKnownMessage = "Unknown message";
        int unKnownAvatarId = R.drawable.unknown_user;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCreated = itemView.findViewById(R.id.txtCreated);
            imgLeft = itemView.findViewById(R.id.imgLeft);
            imgRight = itemView.findViewById(R.id.imgRight);
            txtMessage = itemView.findViewById(R.id.txtMessage);
        }

        public void populate(ProductChatItem item) {
            if (item == null) {
                txtMessage.setText(unKnownMessage);
                return;
            }

            txtMessage.setText(item.getMessage());
            txtCreated.setText(item.getFormatDate());
            txtCreated.setVisibility(View.INVISIBLE);
            if (item.getRole() == ProductChatItem.SENDER) {
                imgRight.setVisibility(View.VISIBLE);
                imgLeft.setVisibility(View.INVISIBLE);
                LoadImageUtils.loadImageFromUrl(item.getAvatar(), imgRight, null);
            } else {
                imgLeft.setVisibility(View.VISIBLE);
                imgRight.setVisibility(View.INVISIBLE);
                LoadImageUtils.loadImageFromUrl(item.getAvatar(), imgLeft, null);
            }
        }

    }

}
