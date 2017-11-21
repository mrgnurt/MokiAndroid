package com.coho.moki.ui.start_tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import com.github.siyamed.shapeimageview.CircularImageView;

public class CircularImageViewFrame extends CircularImageView {
    Frame frame;

    public CircularImageViewFrame(Context context) {
        super(context);
    }

    public CircularImageViewFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFrame(Frame frame) {
        LayoutParams param = getLayoutParams();
        if (param != null) {
            param.width = frame.width;
            param.height = frame.height;
            setX((float) frame.x);
            setY((float) frame.y);
            setLayoutParams(param);
        }
    }

    public Frame getFrame() {
        return this.frame;
    }

    public float getY() {
        if (this.frame != null) {
            return (float) this.frame.y;
        }
        return super.getY();
    }

    public float getX() {
        if (this.frame != null) {
            return (float) this.frame.x;
        }
        return super.getX();
    }
}
