package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coho.moki.R;
import com.github.siyamed.shapeimageview.CircularImageView;

/**
 * Created by son on 15/11/2017.
 */

public class FollowedAdapter extends ArrayAdapter {

    LayoutInflater mLayoutInflater;


    public FollowedAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    // gan lai view cua tung item
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FollowedAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.follow_user_item, parent, false);
            viewHolder = new FollowedAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FollowedAdapter.ViewHolder) convertView.getTag();
        }
        bindItem(viewHolder, position);
        return convertView;
    }

    private void bindItem(FollowedAdapter.ViewHolder viewHolder, int position) {
        viewHolder.txtName.setText("Followed User");

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
