package com.coho.moki.ui.start_tutorial;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import java.util.Iterator;
import java.util.LinkedList;

public class Pager extends HorizontalScrollView {
    private LinearLayout contents;
    private LinkedList<OnPageChangeListener> listeners;

    public Pager(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        this.contents = new LinearLayout(ctx);
        this.contents.setLayoutParams(new LayoutParams(-2, -1));
        addView(this.contents);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < this.contents.getChildCount(); i++) {
            View child = this.contents.getChildAt(i);
            if (child.getLayoutParams().width != specSize) {
                child.setLayoutParams(new LinearLayout.LayoutParams(specSize, -1));
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected float getLeftFadingEdgeStrength() {
        return 0.0f;
    }

    protected float getRightFadingEdgeStrength() {
        return 0.0f;
    }

    public void addPage(View child) {
        child.setLayoutParams(new LayoutParams(getWidth(), -1));
        this.contents.addView(child);
        this.contents.requestLayout();
        firePageCountChanged();
    }

    public boolean onTouchEvent(MotionEvent evt) {
        boolean result = super.onTouchEvent(evt);
        int width = getWidth();
        if (evt.getAction() == 1) {
            smoothScrollTo(((getScrollX() + (width / 2)) / width) * width, 0);
        }
        return result;
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (this.listeners != null) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                OnPageChangeListener list = (OnPageChangeListener) it.next();
                list.onPageChange(this);
                list.pageScroll(l, t, oldl, oldt);
            }
        }
    }

    public boolean hasPage(View v) {
        return this.contents.indexOfChild(v) != -1;
    }

    public void removePage(View v) {
        this.contents.removeView(v);
        firePageCountChanged();
    }

    public int getCurrentPage() {
        int width = getWidth();
        return (getScrollX() + (width / 2)) / width;
    }

    public int getPageCount() {
        return this.contents.getChildCount();
    }

    public void removeAllPages() {
        this.contents.removeAllViews();
        firePageCountChanged();
    }

    private void firePageCountChanged() {
        if (this.listeners != null) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((OnPageChangeListener) it.next()).onPageCountChange(this);
            }
        }
    }

    public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (this.listeners == null) {
            this.listeners = new LinkedList();
        }
        this.listeners.add(onPageChangeListener);
    }

    public boolean removeOnPageChangeListener(OnPageChangeListener l) {
        if (this.listeners != null) {
            return this.listeners.remove(l);
        }
        return false;
    }
}
