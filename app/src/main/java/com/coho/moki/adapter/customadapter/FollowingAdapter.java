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

public class FollowingAdapter extends ArrayAdapter {

    LayoutInflater mLayoutInflater;


    public FollowingAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    // gan lai view cua tung item
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FollowingAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.follow_user_item, parent, false);
            viewHolder = new FollowingAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FollowingAdapter.ViewHolder) convertView.getTag();
        }
        bindItem(viewHolder, position);
        return convertView;
    }

    private void bindItem(FollowingAdapter.ViewHolder viewHolder, int position) {
        viewHolder.txtName.setText("User Following");

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
