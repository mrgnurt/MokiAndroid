package com.coho.moki.ui.custom;

import android.content.Context;
import android.util.AttributeSet;

public class ImageViewRatio extends android.support.v7.widget.AppCompatImageView {
    private boolean ratioHeight;
    private float ratioSize;
    private boolean ratioWidth;

    public ImageViewRatio(Context context) {
        super(context);
    }

//    public ImageViewRatio(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageViewRatio, 0, 0);
//        this.ratioWidth = a.getBoolean(R.styleable.ImageViewRatio_ratioWidth, false);
//        this.ratioHeight = a.getBoolean(R.styleable.ImageViewRatio_ratioHeight, false);
//        this.ratioSize = a.getFloat(R.styleable.ImageViewRatio_ratioSize, 1.0f);
//        a.recycle();
//    }

    public ImageViewRatio(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.ratioWidth) {
            setMeasuredDimension(getMeasuredWidth(), (int) (((float) getMeasuredWidth()) / this.ratioSize));
        } else if (this.ratioHeight) {
            setMeasuredDimension((int) (((float) getMeasuredHeight()) / this.ratioSize), getMeasuredHeight());
        } else {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        }
    }
}
