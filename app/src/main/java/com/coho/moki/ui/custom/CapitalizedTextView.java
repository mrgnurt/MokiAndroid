package com.coho.moki.ui.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CapitalizedTextView extends AppCompatTextView {
    public CapitalizedTextView(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context
     */
    public CapitalizedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context
     */
    public CapitalizedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text.toString().toUpperCase(), type);
    }
}
