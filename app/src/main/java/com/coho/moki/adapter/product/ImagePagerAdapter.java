package com.coho.moki.adapter.product;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coho.moki.R;
import com.coho.moki.util.network.LoadImageUtils;

import java.util.List;

/**
 * Created by Khanh Nguyen on 10/18/2017.
 */

public class ImagePagerAdapter extends PagerAdapter {

    private List<String> imgList;
    private LayoutInflater inflater;

    public ImagePagerAdapter(Context context, List<String> imgList) {
        this.imgList = imgList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.image_item, container, false);
        ImageView imgView = view.findViewById(R.id.imgItem);
//        LoadImageUtils.loadImageFromDrawable(imgList.get(position), imgView);
        LoadImageUtils.loadImageFromUrl(imgList.get(position), imgView, null);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
