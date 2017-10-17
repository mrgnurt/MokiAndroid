package com.coho.moki.ui.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by trung on 13/10/2017.
 */

public class SquareImageView extends AppCompatImageView {

    private static final String TAG = SquareImageView.class.getSimpleName();

    public SquareImageView(Context context) {

        super(context);

    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);

    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

    }

//     require min API 21, current: API 18

//    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {

//        super(context, attrs, defStyleAttr, defStyleRes);

//    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        Log.d(TAG, "width = " + widthMeasureSpec);

//        Log.d(TAG, "height = " + heightMeasureSpec);

        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());

        setMeasuredDimension(size, size);

    }
}
