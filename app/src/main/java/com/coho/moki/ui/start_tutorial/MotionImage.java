package com.coho.moki.ui.start_tutorial;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class MotionImage extends AppCompatImageView {
    private static final int MOTION_TYPE_SLICE = 1;
    private static final int MOTION_TYPE_ZOOM = 0;
    public Frame desirePosition;
    public float motionRate;
    LayoutParams param;
    public Frame startPosition;

    public MotionImage(Context context) {
        super(context);
        init(null, 0);
    }

    public MotionImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MotionImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
    }

    public void moving(float distance) {
        if ((this.motionRate != 0.0f ? 1 : 0) == 1) {
            setX(((float) this.desirePosition.f170x) + (this.motionRate * distance));
        } else if (distance < 0.0f) {
            setX(((float) this.desirePosition.f170x) + distance);
        } else {
            float zoom = distance / ((float) getContext().getResources().getDisplayMetrics().widthPixels);
            int x = (int) (((float) this.desirePosition.f170x) + (((float) (this.startPosition.f170x - this.desirePosition.f170x)) * zoom));
            int y = (int) (((float) this.desirePosition.f171y) + (((float) (this.startPosition.f171y - this.desirePosition.f171y)) * zoom));
            int width = (int) (((float) this.desirePosition.width) + (((float) (this.startPosition.width - this.desirePosition.width)) * zoom));
            int height = (int) (((float) this.desirePosition.height) + (((float) (this.startPosition.height - this.desirePosition.height)) * zoom));
            this.param = getLayoutParams();
            this.param.width = width;
            this.param.height = height;
            setX((float) x);
            setY((float) y);
            setLayoutParams(this.param);
        }
    }

    public void setupZoomArea(Frame startPosition, Frame endPosition) {
        this.startPosition = startPosition;
        this.desirePosition = endPosition;
    }

    public void setupPostion(Frame positionAndSize, float motionRate) {
        this.desirePosition = positionAndSize;
        this.motionRate = motionRate;
        int devWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        setX(((float) positionAndSize.f170x) * motionRate);
        setY((float) positionAndSize.f171y);
    }
}
