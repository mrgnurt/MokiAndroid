package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.UserFollowResponseData;
import com.coho.moki.ui.login.LoginActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.network.LoadImageUtils;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by son on 15/11/2017.
 */

public class FollowedAdapter extends ArrayAdapter {

    LayoutInflater mLayoutInflater;
    ArrayList<UserFollowResponseData> userFollowedList = null;
    Context context = null;

    public FollowedAdapter(@NonNull Context context, int resource, ArrayList<UserFollowResponseData> userFolloweds) {
        super(context, resource);
        this.context = context;
        userFollowedList = userFolloweds;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userFollowedList != null ? userFollowedList.size() : 0;
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

    private void bindItem(final FollowedAdapter.ViewHolder viewHolder, int position) {
        final UserFollowResponseData userFollowResponseData = userFollowedList.get(position);
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
                if (AccountUntil.getUserToken() != null) {
                    if (userFollowResponseData.getFollowed() == 1) {
                        DialogUtil.showPopupSuccess(context, AppConstant.POPUP_UNFOLLOW);
                        userFollowResponseData.setFollowed(0);
                        viewHolder.btnAction.setBackgroundResource(R.drawable.add);
                    } else {
                        DialogUtil.showPopupSuccess(context, AppConstant.POPUP_FOLLOW + viewHolder.txtName.getText().toString());
                        userFollowResponseData.setFollowed(1);
                        viewHolder.btnAction.setBackgroundResource(R.drawable.delete);
                    }
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
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
