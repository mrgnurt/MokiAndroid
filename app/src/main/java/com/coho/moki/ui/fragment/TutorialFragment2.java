package com.coho.moki.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.model.Image;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.start_tutorial.CircularImageViewFrame;
import com.coho.moki.ui.start_tutorial.Frame;
import com.coho.moki.ui.start_tutorial.ImageViewFrame;
import com.coho.moki.ui.start_tutorial.MotionImage;
import com.coho.moki.ui.start_tutorial.MotionTextView;
import com.coho.moki.ui.start_tutorial.PageIndicator;
import com.coho.moki.ui.start_tutorial.Pager;
import com.coho.moki.ui.start_tutorial.StartTutorialActivity;
import com.coho.moki.ui.start_tutorial.StartTutorialActivity2;

import java.util.ArrayList;
import java.util.Timer;

import butterknife.BindView;

/**
 * Created by trung on 10/11/2017.
 */

public class TutorialFragment2 extends BaseFragment {

    @BindView(R.id.productImgScroll)
    ScrollView productImgScroll;

    @BindView(R.id.ava1)
    ImageView ava1;

    @BindView(R.id.ava2)
    ImageView ava2;

    @BindView(R.id.ava3)
    ImageView ava3;

    @BindView(R.id.ava4)
    ImageView ava4;

    @BindView(R.id.ava5)
    ImageView ava5;

    int currentZoomingIconIndex = 0;
    private boolean stopZoomReverse = false;
    private boolean upZoom = true;
    ImageView zoomingIcon = null;

    private boolean stopZoom  =false;
    private boolean isStoppedZooming = true;

    Animation animation;

    class ZoomListenner implements Animator.AnimatorListener {

        class ResetRunnable implements Runnable {
            ResetRunnable() {
            }

            public void run() {
                Log.d("trung", "endrun");

                TutorialFragment2.this.zoomIcon();
            }
        }

        ZoomListenner() {
        }

        public void onAnimationStart(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
//            if (!(TutorialFragment2.this.currentZoomingIconIndex == 0 && TutorialFragment2.this.stopZoomReverse) && (TutorialFragment2.this.currentZoomingIconIndex != 4 || TutorialFragment2.this.stopZoomReverse)) {
//                TutorialFragment2.this.zoomIcon();
//            } else {
//                new Handler().postDelayed(new TutorialFragment2.ZoomListenner.ResetRunnable(), 1000);
//            }

            if ((TutorialFragment2.this.currentZoomingIconIndex == 0 && upZoom) || (TutorialFragment2.this.currentZoomingIconIndex == 4 && !upZoom) ){
                new Handler().postDelayed(new TutorialFragment2.ZoomListenner.ResetRunnable(), 1000);

            }
            else {
                TutorialFragment2.this.zoomIcon();
            }
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_tutorial2;
    }

    @Override
    protected void initView() {
        super.initView();

        autoScrollProductList();
        initAnimation();
    }

    private void autoScrollProductList() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this.productImgScroll, "scrollY", 0, 400).setDuration(2000);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

    private void initAnimation(){

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this.ava1, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("scaleX", new float[]{1.0f, 1.5f}), PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 1.5f})});
        objectAnimator.setDuration(250);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                currentZoomingIconIndex = 1;
                TutorialFragment2.this.zoomIcon();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();


//        animation = AnimationUtils.loadAnimation(BaseApp.getContext(), R.anim.zoom_icon);
//        animation = new ScaleAnimation(1f, 2f, 1f, 2f);
//        animation.setDuration(3000);
//        mFirstImg.startAnimation(animation);
//        animation = new ScaleAnimation(2f, 1f, 2f, 1f);
//        animation.setStartOffset(3000);
//        mFirstImg.startAnimation(animation);
    }

    private void zoomIcon() {

        if (upZoom){
            switch (this.currentZoomingIconIndex) {
                case 0:
                    this.zoomingIcon = this.ava1;
                    this.currentZoomingIconIndex++;
                    break;
                case 1:
                    this.zoomingIcon = this.ava2;
                    this.currentZoomingIconIndex++;
                    break;
                case 2:
                    this.zoomingIcon = this.ava3;
                    this.currentZoomingIconIndex++;
                    break;
                case 3:
                    this.zoomingIcon = this.ava4;
                    this.currentZoomingIconIndex++;
                    break;
                case 4:
                    this.zoomingIcon = this.ava5;
                    upZoom = false;
                    break;
                default:
                    break;
            }
        }
        else {
            switch (this.currentZoomingIconIndex) {
                case 0:
                    this.zoomingIcon = this.ava1;
                    upZoom = true;
                    break;
                case 1:
                    this.zoomingIcon = this.ava2;
                    this.currentZoomingIconIndex--;
                    break;
                case 2:
                    this.zoomingIcon = this.ava3;
                    this.currentZoomingIconIndex--;
                    break;
                case 3:
                    this.zoomingIcon = this.ava4;
                    this.currentZoomingIconIndex--;
                    break;
                case 4:
                    this.zoomingIcon = this.ava5;
                    this.currentZoomingIconIndex--;
                    break;
                default:
                    break;
            }
        }
        
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this.zoomingIcon, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("scaleX", new float[]{1.0f, 1.5f}), PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 1.5f})});
        objectAnimator.setDuration(250);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.addListener(new TutorialFragment2.ZoomListenner());
        objectAnimator.start();
    }
}
