package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.remote.UserFollowResponseData;
import com.coho.moki.util.network.LoadImageUtils;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by son on 15/11/2017.
 */

public class FollowingAdapter extends ArrayAdapter {

    LayoutInflater mLayoutInflater;
    ArrayList<UserFollowResponseData> userFollowingList = null;


    public FollowingAdapter(@NonNull Context context, int resource, ArrayList<UserFollowResponseData> userFollowings) {
        super(context, resource);
        userFollowingList = userFollowings;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userFollowingList != null ? userFollowingList.size() : 0;
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
        UserFollowResponseData userFollowResponseData = userFollowingList.get(position);
        viewHolder.txtName.setText(userFollowResponseData.getUsername());
        LoadImageUtils.loadImageFromUrl(userFollowResponseData.getAvatar(), R.drawable.unknown_user, viewHolder.circularImageView, null);
        if (userFollowResponseData.getFollowed() == 1) {
            viewHolder.btnAction.setBackgroundResource(R.drawable.delete);
        } else {
            viewHolder.btnAction.setBackgroundResource(R.drawable.add);
        }
    }

    private static class ViewHolder {
        private CircularImageView circularImageView;
        private TextView txtName;
        private Button btnAction;

        private ViewHolder(View view) {
            circularImageView = view.findViewById(R.id.imgAvatar);
            txtName = view.findViewById(R.id.txtName);
            btnAction = view.findViewById(R.id.btnAction);
        }

    }

}
