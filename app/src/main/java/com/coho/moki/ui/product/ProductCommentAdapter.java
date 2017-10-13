package com.coho.moki.ui.product;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.model.ProductComment;

import java.util.List;

/**
 * Created by Khanh Nguyen on 10/13/2017.
 */

public class ProductCommentAdapter extends ArrayAdapter<ProductComment> {

    private static final String TAG = ProductCommentAdapter.class.getSimpleName();

    LayoutInflater mLayoutInflater;
    List<ProductComment> productCommentList;

    public ProductCommentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ProductComment> objects) {
        super(context, resource, objects);
        mLayoutInflater = LayoutInflater.from(context);
        this.productCommentList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.product_comment_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindItem(viewHolder, position);
        return convertView;
    }

    private void bindItem(ViewHolder viewHolder, int position) {
        ProductComment p = productCommentList.get(position);
        viewHolder.txtName.setText(p.getName());
        viewHolder.imgAvatar.setImageResource(R.drawable.unknown_user);
        viewHolder.txtComment.setText(p.getComment());
        viewHolder.txtTime.setText("10 giờ trước");
        viewHolder.iconClock.setImageResource(R.drawable.icon_clock);
    }

    // optimize performance of ListView
    private static class ViewHolder {
        private TextView txtName;
        private ImageView imgAvatar;
        private TextView txtComment;
        private ImageView iconClock;
        private TextView txtTime;

        private ViewHolder(View convertView) {
            txtName = convertView.findViewById(R.id.txtName);
            imgAvatar = convertView.findViewById(R.id.imgAvatar);
            txtComment = convertView.findViewById(R.id.txtComment);
            iconClock = convertView.findViewById(R.id.icon_clock);
            txtTime = convertView.findViewById(R.id.txtTime);
        }

    }

}
