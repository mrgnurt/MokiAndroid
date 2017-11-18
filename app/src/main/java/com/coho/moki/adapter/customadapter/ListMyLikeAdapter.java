package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.ui.custom.SquareImageView;
import com.coho.moki.ui.size.SizeContract;
import com.coho.moki.util.network.LoadImageUtils;

import java.util.List;

/**
 * Created by trung on 18/11/2017.
 */

public class ListMyLikeAdapter extends BaseAdapter {

    List<MyLikeResponseData> myLikeResponseDataList;
    LayoutInflater mLayoutInflater;

    public ListMyLikeAdapter(Context context, List<MyLikeResponseData> datas){
        mLayoutInflater = LayoutInflater.from(context);
        myLikeResponseDataList = datas;
    }

    @Override
    public int getCount() {

        if (myLikeResponseDataList == null){
            return 0;
        }
        else {
            return myLikeResponseDataList.size();
        }

    }

    @Override
    public Object getItem(int i) {
        return myLikeResponseDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){

            view = mLayoutInflater.inflate(R.layout.my_like_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LoadImageUtils.loadImageFromUrl(myLikeResponseDataList.get(i).getImage().get(0), R.drawable.no_image, viewHolder.imgProduct, null);
        viewHolder.txtNameProduct.setText(myLikeResponseDataList.get(i).getName());
        viewHolder.txtPrice.setText(myLikeResponseDataList.get(i).getPrice() + " VND");

        return view;
    }

    class ViewHolder{
        SquareImageView imgProduct;
        ImageView imgBanner;
        TextView txtNameProduct;
        TextView txtPrice;

        public ViewHolder(View view){

            imgProduct = (SquareImageView) view.findViewById(R.id.imgProduct);
            imgBanner = (ImageView) view.findViewById(R.id.imgBanner);
            txtNameProduct = (TextView) view.findViewById(R.id.txtNameProduct);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        }
    }
}
