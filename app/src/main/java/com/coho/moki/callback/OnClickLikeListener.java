package com.coho.moki.callback;

import android.view.View;

import com.coho.moki.data.remote.ProductSmallResponceData;

/**
 * Created by trung on 17/11/2017.
 */

public interface OnClickLikeListener {
    public void onClick(ProductSmallResponceData productId, View view, boolean isLike);
}
