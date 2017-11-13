package com.coho.moki.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by trung on 16/10/2017.
 */

public class SpaceItem extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItem(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        if (position == 0 || position == 1){
            outRect.top = space;
        }
        else{
            outRect.top = 0;
        }

        if (position % 2 == 0){
            outRect.right = space/2;
            outRect.left = space;
        }
        else{
            outRect.right = space;
            outRect.left = space/2;
        }

        outRect.bottom = space;
    }
}
