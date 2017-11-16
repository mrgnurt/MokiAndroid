package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.callback.OnLoadImageListener;
import com.coho.moki.data.model.Image;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.ChatConsersation;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.util.network.LoadImageUtils;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by trung on 15/11/2017.
 */

public class ListProductTimelineAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    List<ProductSmallResponceData> mProducts;
    LayoutInflater mLayoutInflater;

    public ListProductTimelineAdapter(Context context, List<ProductSmallResponceData> products){
        mProducts = products;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mProducts == null){
            return 0;
        }
        else {
            return mProducts.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return mProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.timeline_1_image_item, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindItem(viewHolder, position);
        return convertView;
    }

    private void bindItem(final ViewHolder viewHolder, int position) {
        ProductSmallResponceData product = mProducts.get(position);

        LoadImageUtils.loadImageFromUrl(product.getImage().get(0), R.drawable.unknown_user, viewHolder.firstImg, new OnLoadImageListener() {
            @Override
            public void onSuccess() {
                viewHolder.frameProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                viewHolder.frameProgress.setVisibility(View.GONE);
            }
        });

        if (product.getPricePercent() == 0){
            viewHolder.sale_off_view_container.setVisibility(View.GONE);
        }
        else {
            viewHolder.salePercent.setText(product.getPricePercent() + "%");
        }

        if (product.getIsLiked() == 1){
            viewHolder.btnLike.setBackgroundResource(R.drawable.icon_like_on);
        }

        viewHolder.btnLike.setText(product.getLike());
        viewHolder.btnComment.setText(product.getLike());
        viewHolder.txtName.setText(product.getName());
        viewHolder.txtPrice.setText(product.getPrice() + "VND");
        viewHolder.txtDescription.setText(product.getDescribed());
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.home_product_timeline_header, parent, false);
            viewHolder = new HeaderViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }
        bindItemHeader(viewHolder, position);
        return convertView;
    }

    private void bindItemHeader(HeaderViewHolder viewHolder, int position) {
        ProductSmallResponceData product = mProducts.get(position);

        LoadImageUtils.loadImageFromUrl(product.getSeller().getAvatar(),R.drawable.unknown_user, viewHolder.seller_avatar, null );
        viewHolder.posted_time.setText(product.getCreated());
        viewHolder.seller_name.setText(product.getSeller().getUserName());

    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    private static class ViewHolder {
        private ImageView firstImg;
        private FrameLayout frameProgress;
        private RelativeLayout sale_off_view_container;
        private TextView salePercent;
        private TextView txtName;
        private TextView txtPrice;
        private TextView txtDescription;
        private Button btnLike;
        private Button btnComment;
        private Button btnEdit;


        private ViewHolder(View view) {
            firstImg = (ImageView) view.findViewById(R.id.first_image);
            frameProgress = (FrameLayout) view.findViewById(R.id.frameProgress);
            sale_off_view_container = (RelativeLayout) view.findViewById(R.id.sale_off_view_container);
            salePercent = (TextView) view.findViewById(R.id.salePercent);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            txtDescription = (TextView) view.findViewById(R.id.txtDescription);
            btnLike = (Button) view.findViewById(R.id.btnLike);
            btnComment = (Button) view.findViewById(R.id.btnComment);
            btnEdit = (Button) view.findViewById(R.id.btnEdit);
        }

    }

    private static class HeaderViewHolder {
        private CircularImageView seller_avatar;
        private TextView posted_time;
        private TextView seller_name;

        private HeaderViewHolder(View view) {
            seller_avatar = (CircularImageView) view.findViewById(R.id.seller_avatar);
            posted_time = (TextView) view.findViewById(R.id.posted_time);
            seller_name = (TextView) view.findViewById(R.id.seller_name);
        }

    }

    public void insertLastItem(List<ProductSmallResponceData> products){

        if (products != null && products.size() > 0){

            mProducts.addAll(products);
            notifyDataSetChanged();
        }
    }
}
