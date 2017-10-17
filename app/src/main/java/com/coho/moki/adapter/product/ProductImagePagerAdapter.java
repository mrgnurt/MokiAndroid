package com.coho.moki.adapter.product;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coho.moki.R;

import java.util.List;

/**
 * Created by Khanh Nguyen on 10/13/2017.
 */

public class ProductImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<Integer> imgList;
    private LayoutInflater inflater;

    public ProductImagePagerAdapter(Context context, List<Integer> imgList) {
        this.context = context;
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
        View view = inflater.inflate(R.layout.slide_image, container, false);
        ImageView imgView = view.findViewById(R.id.imgItem);
        imgView.setImageResource(imgList.get(position));
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
