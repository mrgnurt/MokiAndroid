package com.coho.moki.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.coho.moki.R;

/**
 * Created by trung on 14/11/2017.
 */

public class MyProgressDialog extends ProgressDialog {

    private AnimationDrawable animation;

    public MyProgressDialog(Context context) {
        super(context);
    }


    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_custom_progress_dialog);
        setIndeterminate(true);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ImageView animate = (ImageView) findViewById(R.id.animation);
        animate.setBackgroundResource(R.drawable.loading_animation);
        this.animation = (AnimationDrawable) animate.getBackground();
    }

    public void show() {
        super.show();
        this.animation.start();
    }

    public void dismiss() {
        super.dismiss();
        this.animation.stop();
    }

    public void cancel() {
        super.cancel();
        this.animation.stop();
    }
}
