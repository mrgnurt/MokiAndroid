package com.coho.moki.ui.start_tutorial;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ImageViewFrame extends AppCompatImageView {
    Frame frame;

    public ImageViewFrame(Context context) {
        super(context);
    }

    public ImageViewFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setFrame(Frame frame) {
        LayoutParams param = getLayoutParams();
        if (param != null) {
            param.width = frame.width;
            param.height = frame.height;
            setX((float) frame.f170x);
            setY((float) frame.f171y);
            setLayoutParams(param);
        }
    }

    public Frame getFrame() {
        return this.frame;
    }

    public float getY() {
        if (this.frame != null) {
            return (float) this.frame.f171y;
        }
        return super.getY();
    }

    public float getX() {
        if (this.frame != null) {
            return (float) this.frame.f170x;
        }
        return super.getX();
    }
}
