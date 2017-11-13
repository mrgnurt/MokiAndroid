package com.coho.moki.ui.start_tutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

import com.coho.moki.R;

public class InfiniteScrollImage extends AppCompatImageView {
    static int delta = 0;
    private Handler handler = new Handler();
    private Bitmap myBitmap;
    private Runnable runnable = new C28491();

    class C28491 implements Runnable {
        C28491() {
        }

        public void run() {
            InfiniteScrollImage.this.invalidate();
            InfiniteScrollImage.this.handler.postDelayed(InfiniteScrollImage.this.runnable, 1);
        }
    }

    public InfiniteScrollImage(Context context) {
        super(context);
        init(context);
    }

    public InfiniteScrollImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InfiniteScrollImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
//
    private void init(Context ctx) {
        this.handler.postDelayed(this.runnable, 1);
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        this.myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.list_product2);
        this.myBitmap = Bitmap.createScaledBitmap(this.myBitmap, metrics.widthPixels, metrics.heightPixels, false);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    protected void onDraw(Canvas canvas) {
        float w = (float) getWidth();
        float h = (float) getHeight();
        canvas.drawBitmap(this.myBitmap, 0.0f, (float) delta, null);
        canvas.drawBitmap(this.myBitmap, 0.0f, (float) (delta + this.myBitmap.getHeight()), null);
        delta -= 5;
        if (delta < (-this.myBitmap.getHeight())) {
            delta = 0;
        }
    }
}
