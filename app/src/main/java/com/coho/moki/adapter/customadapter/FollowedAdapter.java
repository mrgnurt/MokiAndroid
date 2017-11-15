package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.remote.ChatConsersation;
import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by son on 15/11/2017.
 */

public class FollowedAdapter extends ArrayAdapter {

    LayoutInflater mLayoutInflater;


    public FollowedAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FollowedAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.notification_item, parent, false);
            viewHolder = new FollowedAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FollowedAdapter.ViewHolder) convertView.getTag();
        }
        bindItem(viewHolder, position);
        return convertView;
    }

    private void bindItem(FollowedAdapter.ViewHolder viewHolder, int position) {
        viewHolder.txtName.setText("Son");

    }

    private static class ViewHolder {
        private CircularImageView circularImageView;
        private TextView txtName;


        private ViewHolder(View view) {
            circularImageView = view.findViewById(R.id.imgAvatar);
            txtName = view.findViewById(R.id.txtName);
        }

    }
}
