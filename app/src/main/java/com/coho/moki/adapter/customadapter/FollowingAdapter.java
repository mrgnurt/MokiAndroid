package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.UserFollowResponseData;
import com.coho.moki.ui.user.UserInfoActivity;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.network.LoadImageUtils;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by son on 15/11/2017.
 */

public class FollowingAdapter extends ArrayAdapter {

    LayoutInflater mLayoutInflater;
    ArrayList<UserFollowResponseData> userFollowingList = null;
    Context context = null;


    public FollowingAdapter(@NonNull Context context, int resource, ArrayList<UserFollowResponseData> userFollowings) {
        super(context, resource);
        this.context = context;
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

    private void bindItem(final FollowingAdapter.ViewHolder viewHolder, int position) {
        final UserFollowResponseData userFollowResponseData = userFollowingList.get(position);
        viewHolder.txtName.setText(userFollowResponseData.getUsername());
        LoadImageUtils.loadImageFromUrl(userFollowResponseData.getAvatar(), R.drawable.unknown_user, viewHolder.circularImageView, null);
        if (userFollowResponseData.getFollowed() == 1) {
            viewHolder.btnAction.setBackgroundResource(R.drawable.delete);
        } else {
            viewHolder.btnAction.setBackgroundResource(R.drawable.add);
        }

        viewHolder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userFollowResponseData.getFollowed() == 1) {
                    DialogUtil.showPopupSuccess(context, AppConstant.POPUP_UNFOLLOW);
                    userFollowResponseData.setFollowed(0);
                    viewHolder.btnAction.setBackgroundResource(R.drawable.add);
                } else {
                    DialogUtil.showPopupSuccess(context, AppConstant.POPUP_FOLLOW + viewHolder.txtName.getText().toString());
                    userFollowResponseData.setFollowed(1);
                    viewHolder.btnAction.setBackgroundResource(R.drawable.delete);
                }
            }
        });
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
