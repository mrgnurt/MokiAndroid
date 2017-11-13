package com.coho.moki.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coho.moki.R;
import com.coho.moki.callback.OnClickProductImage;
import com.coho.moki.callback.OnLoadImageListener;
import com.coho.moki.ui.product.MediaActivity;
import com.coho.moki.util.network.LoadImageUtils;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khanh Nguyen on 10/31/2017.
 */

public class ProductImageFragment extends Fragment {

    private Integer imgPos;
    private List<String> imgList;
//    private OnClickProductImage onClickProductImage;

    public static ProductImageFragment newInstance(List<String> imgList, Integer imagePosition) {
        ProductImageFragment fm = new ProductImageFragment();
        Bundle args = new Bundle();
        args.putInt("imgPos", imagePosition);
        args.putStringArrayList("imgList", new ArrayList<>(imgList));
        fm.setArguments(args);
        return fm;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        imgPos = args.getInt("imgPos");
        imgList = args.getStringArrayList("imgList");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_image, container, false);
        ImageView imageView = view.findViewById(R.id.imgItem);

//        String[] url = {"http://image.24h.com.vn/upload/4-2017/images/2017-11-01/1509530452-150952949815900-kh--nh-ph----ng1.jpg",
//                "http://image.24h.com.vn/upload/4-2017/images/2017-11-01/1509530452-150952950554120-kh--nh-ph----ng2.jpg",
//        "http://2sao.vietnamnetjsc.vn/images/2017/10/25/21/43/huyen-mi1.png",
//        "https://instagram.fhan2-2.fna.fbcdn.net/t51.2885-15/e35/22344128_1947172392196415_145543165917528064_n.jpg",
//        "https://instagram.fhan2-2.fna.fbcdn.net/t51.2885-15/s640x640/sh0.08/e35/c0.117.1080.1080/21879202_274941629660876_5949547367396016128_n.jpg"};
//        if (imgPos <= 4) {
//            LoadImageUtils.loadImageFromUrl(url[imgPos], imageView, null);
//        } else {
//            LoadImageUtils.loadImageFromDrawable(imgResList.get(imgPos), imageView);
//        }
        LoadImageUtils.loadImageFromUrl(imgList.get(imgPos), imageView, null);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Intent intent = new Intent(context, MediaActivity.class);
                ArrayList<String> mList = new ArrayList<>(imgList);
                intent.putStringArrayListExtra("listImage", mList);
                context.startActivity(intent);
            }
        });
        return view;
    }

}