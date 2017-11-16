package com.coho.moki.util.network;

import android.widget.ImageView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.callback.OnLoadImageListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Khanh Nguyen on 11/1/2017.
 */

public final class LoadImageUtils {

    private LoadImageUtils() {}

    public static void  loadImageFromDrawable(int resId, final ImageView imgView){
        Picasso.with(BaseApp.getContext()).load(resId).into(imgView);
    }

    public static void loadImageFromUrl(String url, final ImageView imgView, final OnLoadImageListener listener) {
        Picasso picasso;
        picasso = Picasso.with(BaseApp.getContext());
        picasso.load(url)
                .fit()
                .placeholder(R.drawable.no_image)
                .into(imgView, listener);
    }

    public static void loadImageFromUrl(String url, int placeholder, final ImageView imgView, final OnLoadImageListener listener) {
        Picasso.with(BaseApp.getContext())
                .load(url)
                .fit()
                .placeholder(placeholder)
                .into(imgView, listener);
    }

}
