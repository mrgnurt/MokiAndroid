package com.coho.moki.adapter.user;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.remote.UserRateResponse;
import com.coho.moki.util.network.LoadImageUtils;

import java.util.List;

/**
 * Created by Khanh Nguyen on 11/8/2017.
 */

public class UserRateAdapter extends ArrayAdapter<UserRateResponse> {

    private static final String TAG = "UserRateAdapter";

    LayoutInflater mLayoutInflater;
    List<UserRateResponse> mUserRateList;

    public UserRateAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<UserRateResponse> objects) {
        super(context, resource, objects);
        mLayoutInflater = LayoutInflater.from(context);
        this.mUserRateList = objects;
    }

    /**
     * Return a view: view item in ListView
     * @param position position in the product list
     * @param convertView is new view inflate from xml layout, or scrapview in case reuse
     * @param parent parent view, in this case is ListView
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.user_rate_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindItem(viewHolder, position);
        return convertView;
    }

    private void bindItem(ViewHolder viewHolder, int position) {
//        UserRateResponse response = mUserRateList.get(position);
//        ProductCommentResponse.Poster poster = comment.getPoster();
//        viewHolder.txtName.setText(poster.getName());
//        LoadImageUtils.loadImageFromUrl(poster.getAvatar(), R.drawable.unknown_user, viewHolder.imgAvatar, null);
//        viewHolder.txtComment.setText(comment.getComment());
//        viewHolder.txtTime.setText(Utils.formatTime(comment.getCreated()));
        LoadImageUtils.loadImageFromDrawable(R.drawable.unknown_user, viewHolder.imgAvatar);
        LoadImageUtils.loadImageFromDrawable(R.drawable.status_product_icon, viewHolder.iconStatus);
        viewHolder.txtName.setText("My Name");
        viewHolder.txtContent.setText("Content rate");
        viewHolder.txtTime.setText("5 giờ trước");
        viewHolder.iconClock.setImageResource(R.drawable.icon_clock);
    }

    // using ViewHolder pattern to optimize performance of ListView
    private static class ViewHolder {
        private ImageView imgAvatar;
        private ImageView iconStatus;
        private TextView txtName;
        private TextView txtContent;
        private ImageView iconClock;
        private TextView txtTime;

        private ViewHolder(View v) {
            imgAvatar = v.findViewById(R.id.imgAvatar);
            iconStatus = v.findViewById(R.id.iconStatus);
            txtName = v.findViewById(R.id.txtName);
            txtContent = v.findViewById(R.id.txtContent);
            iconClock = v.findViewById(R.id.icon_clock);
            txtTime = v.findViewById(R.id.txtTime);
        }

    }

}
