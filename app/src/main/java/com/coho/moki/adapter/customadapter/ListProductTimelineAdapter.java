package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductChatAdapter;
import com.coho.moki.callback.OnClickCommentListener;
import com.coho.moki.callback.OnClickLikeListener;
import com.coho.moki.callback.OnClickProductItemListenner;
import com.coho.moki.callback.OnLoadImageListener;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Image;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.ChatConsersation;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.ui.product.ProductDetailActivity;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.coho.moki.util.network.LoadImageUtils;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by trung on 15/11/2017.
 */

public class ListProductTimelineAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    public List<ProductSmallResponceData> mProducts;
    LayoutInflater mLayoutInflater;
    OnClickProductItemListenner mOnClickProductItemListenner;
    OnClickLikeListener mOnClickLikeListener;
    OnClickCommentListener mOnClickCommentListener;
    ProductChatAdapter.ItemClickListener listener;

    public void setListener(ProductChatAdapter.ItemClickListener listener) {
        this.listener = listener;
    }

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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        final ProductSmallResponceData product = mProducts.get(position);
        int countImage = product.getImage().size();

//        if (convertView == null) {

            switch (countImage){
                case 1:
                    convertView = mLayoutInflater.inflate(R.layout.timeline_1_image_item, viewGroup, false);
                    viewHolder = new ViewHolder(convertView);
                    viewHolder.firstImg = (ImageView) convertView.findViewById(R.id.firstImg);
                    break;
                case 2:
                    convertView = mLayoutInflater.inflate(R.layout.timeline_2_image_item, viewGroup, false);
                    viewHolder = new ViewHolder(convertView);
                    viewHolder.first2Img = (ImageView) convertView.findViewById(R.id.first2Img);
                    viewHolder.second2Img = (ImageView) convertView.findViewById(R.id.second2Img);
                    break;
                case 3:
                    convertView = mLayoutInflater.inflate(R.layout.timeline_3_image_item, viewGroup, false);
                    viewHolder = new ViewHolder(convertView);
                    viewHolder.first3Img = (ImageView) convertView.findViewById(R.id.first3Img);
                    viewHolder.second3Img = (ImageView) convertView.findViewById(R.id.second3Img);
                    viewHolder.third3Img = (ImageView) convertView.findViewById(R.id.third3Img);
                    break;
                default:
                    convertView = mLayoutInflater.inflate(R.layout.timeline_4_image_item, viewGroup, false);
                    viewHolder = new ViewHolder(convertView);
                    viewHolder.first4Img = (ImageView) convertView.findViewById(R.id.first4Img);
                    viewHolder.second4Img = (ImageView) convertView.findViewById(R.id.second4Img);
                    viewHolder.third4Img = (ImageView) convertView.findViewById(R.id.third4Img);
                    viewHolder.fouthImg = (ImageView) convertView.findViewById(R.id.fouthImg);
                    break;
            }
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        bindItem(viewHolder, position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickProductItemListenner.onClick(product.getId());
            }
        });

        viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getIsLiked() == 0){
                    mOnClickLikeListener.onClick(product, viewHolder.btnLike, true);
                }
                else {

                    mOnClickLikeListener.onClick(product, viewHolder.btnLike, false);
                }

            }
        });

        viewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickCommentListener.onClick(product.getId());
            }
        });
        return convertView;
    }

    private void bindItem(final ViewHolder viewHolder, int position) {
        ProductSmallResponceData product = mProducts.get(position);
//        Log.d("trung", " " + position);
        int countImage = product.getImage().size();

        switch (countImage){
            case 1:
//                Log.d("trung", product.getImage().get(0) + " 1 " + position);
                LoadImageUtils.loadImageFromUrl(product.getImage().get(0), R.drawable.no_image, viewHolder.firstImg, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                        viewHolder.frameProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        viewHolder.frameProgress.setVisibility(View.GONE);
                    }
                });
                break;

            case 2:
                LoadImageUtils.loadImageFromUrl(product.getImage().get(0), R.drawable.no_image, viewHolder.first2Img, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                        viewHolder.frameProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        viewHolder.frameProgress.setVisibility(View.GONE);
                    }
                });

                LoadImageUtils.loadImageFromUrl(product.getImage().get(1), R.drawable.no_image, viewHolder.second2Img, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });
                break;

            case 3:
//                Log.d("trung", product.getImage().get(0) + " 3 " + position);
                LoadImageUtils.loadImageFromUrl(product.getImage().get(0), R.drawable.no_image, viewHolder.first3Img, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                        viewHolder.frameProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        viewHolder.frameProgress.setVisibility(View.GONE);
                    }
                });

                LoadImageUtils.loadImageFromUrl(product.getImage().get(1), R.drawable.no_image, viewHolder.second3Img, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

                LoadImageUtils.loadImageFromUrl(product.getImage().get(2), R.drawable.no_image, viewHolder.third3Img, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

                break;
            default:
                LoadImageUtils.loadImageFromUrl(product.getImage().get(0), R.drawable.no_image, viewHolder.first4Img, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                        viewHolder.frameProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        viewHolder.frameProgress.setVisibility(View.GONE);
                    }
                });

                LoadImageUtils.loadImageFromUrl(product.getImage().get(1), R.drawable.no_image, viewHolder.second4Img, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

                LoadImageUtils.loadImageFromUrl(product.getImage().get(2), R.drawable.no_image, viewHolder.third4Img, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

                LoadImageUtils.loadImageFromUrl(product.getImage().get(2), R.drawable.no_image, viewHolder.fouthImg, new OnLoadImageListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });

                break;
        }



        if (product.getPricePercent() == 0){
            viewHolder.sale_off_view_container.setVisibility(View.GONE);
        }
        else {
            viewHolder.salePercent.setText(product.getPricePercent() + "%");
        }

        if (product.getIsLiked() == 1){
            viewHolder.btnLike.setBackgroundResource(R.drawable.icon_time_line_like_on);
        }

        viewHolder.btnLike.setText(product.getLike() + "");
        viewHolder.btnComment.setText(product.getComment() + "");
        viewHolder.txtName.setText(product.getName());
        viewHolder.txtPrice.setText(product.getPrice() + " VND");
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

    private void bindItemHeader(HeaderViewHolder viewHolder, final int position) {
        ProductSmallResponceData product = mProducts.get(position);

        LoadImageUtils.loadImageFromUrl(product.getSeller().getAvatar(),R.drawable.unknown_user, viewHolder.seller_avatar, null );
        viewHolder.posted_time.setText(Utils.formatTime(product.getCreated()));
        viewHolder.seller_name.setText(product.getSeller().getUserName());

        viewHolder.seller_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {

                    listener.onClick(position);
                }
            }
        });

    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    private static class ViewHolder {
        private ImageView firstImg;
        private ImageView first2Img;
        private ImageView second2Img;
        private ImageView first3Img;
        private ImageView second3Img;
        private ImageView third3Img;
        private ImageView first4Img;
        private ImageView second4Img;
        private ImageView third4Img;
        private ImageView fouthImg;
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

    public void insertHeadItem(List<ProductSmallResponceData> products){

//        if (products != null && products.size() > 0){
//
//            mProducts.addAll(0, products);
//            notifyDataSetChanged();
//        }

        mProducts.addAll(products);
        notifyDataSetChanged();
    }

    public void setmOnClickProductItemListenner(OnClickProductItemListenner listenner){
        mOnClickProductItemListenner = listenner;
    }

    public void setmOnClickLikeListener(OnClickLikeListener listener){
        mOnClickLikeListener = listener;
    }

    public void setmOnClickCommentListener(OnClickCommentListener listener){
        mOnClickCommentListener = listener;
    }
}
