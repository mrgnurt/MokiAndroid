package com.coho.moki.ui.start_tutorial;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by trung on 09/11/2017.
 */

public class StartTutorialActivity extends BaseActivity {

    private static final int NUM_PAGES = 4;
    private MotionImage bgImage;
    int currentZoomingIconIndex;
    private int devHeight;
    private int devWidth;
    private MotionImage deviceImage;
    private ImageViewFrame ensureImg;
    private CircularImageViewFrame fifthZoomImg;
    private MotionImage firstImg;
    private CircularImageViewFrame firstZoomImg;
    private CircularImageViewFrame fouthZoomImg;
    private ArrayList<MotionImage> iconList = new ArrayList();
    private PageIndicator indicator;
    private boolean isStoppedZooming;
    private ScrollView productImgScroll;
    private Pager scroller;
    private MotionImage secondImg;
    private CircularImageViewFrame secondZoomImg;
    private Button skipButton;
    private boolean stopZoom;
    private boolean stopZoomReverse;
    private MotionImage thirdImg;
    private ImageViewFrame thirdPageDeviceImage;
    private CircularImageViewFrame thirdZoomImg;
    private Timer timer;
    private TextView tvFourthPage;
    private MotionTextView tvSecondPage;
    private TextView tvThirdPage;
    private Frame zoomFrame;
    int zoomImgSize;
    CircularImageViewFrame zoomingIcon = null;
    private ArrayList<ImageView> zoomingListImg = new ArrayList();

//    class C28511 implements View.OnClickListener {
//        C28511() {
//        }
//
//        public void onClick(View v) {
//            Auth.setStartUpIntroTutCheck(StartUpTutorialActivity.this, AppEventsConstants.EVENT_PARAM_VALUE_YES);
//            StartUpTutorialActivity.this.startActivityFromRight(new Intent(StartUpTutorialActivity.this, RegisterActivity.class));
//        }
//    }

    class C28522 implements View.OnTouchListener {
        C28522() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    class C28564 implements Runnable {

        class C28551 extends TimerTask {

            class C28541 implements Runnable {
                C28541() {
                }

                public void run() {
                    if (StartTutorialActivity.this.scroller.getScrollX() < 700) {
                        StartTutorialActivity.this.scroller.smoothScrollBy(10, 0);
                    } else if (StartTutorialActivity.this.timer != null) {
                        StartTutorialActivity.this.timer.cancel();
                        StartTutorialActivity.this.timer = null;
                    }
                }
            }

            C28551() {
            }

            public void run() {
                StartTutorialActivity.this.runOnUiThread(new C28541());
            }
        }

        C28564() {
        }

        public void run() {
            StartTutorialActivity.this.timer.schedule(new C28551(), 0, 10);
        }
    }

    class C28585 implements Animator.AnimatorListener {

        class C28571 implements Runnable {
            C28571() {
            }

            public void run() {
                StartTutorialActivity.this.zoomIcon();
            }
        }

        C28585() {
        }

        public void onAnimationStart(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
            if (!(StartTutorialActivity.this.currentZoomingIconIndex == 0 && StartTutorialActivity.this.stopZoomReverse) && (StartTutorialActivity.this.currentZoomingIconIndex != 4 || StartTutorialActivity.this.stopZoomReverse)) {
                StartTutorialActivity.this.zoomIcon();
            } else {
                new Handler().postDelayed(new C28571(), 1000);
            }
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }
    }

    @Override
    public int setContentViewId() {
        return R.layout.start_tutorial;
    }

    @Override
    public void initView() {
        this.skipButton = (Button) findViewById(R.id.skip_button);
//        this.skipButton.setOnClickListener(new C28511());
        this.skipButton.setVisibility(View.INVISIBLE);
        DisplayMetrics metrics = getApplication().getResources().getDisplayMetrics();
        this.devWidth = metrics.widthPixels;
        this.devHeight = metrics.heightPixels;
        int device_image_top_margin = this.devHeight / 20;
        this.scroller = (Pager) findViewById(R.id.scrollView);
        this.indicator = (PageIndicator) findViewById(R.id.indicator);
        this.indicator.setPager(this.scroller);
        final FrameLayout zoomAndMotionLayout = (FrameLayout) findViewById(R.id.layout);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        this.deviceImage = (MotionImage) findViewById(R.id.deviceImage);
        int device_image_width = this.devWidth - ((int) (((double) this.devWidth) / 1.7d));
        int device_image_height = (int) (((double) device_image_width) * 2.1d);
        ViewGroup.LayoutParams indicatorParams = this.indicator.getLayoutParams();
        indicatorParams.height = this.devHeight / 25;
        this.indicator.setLayoutParams(indicatorParams);
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                this.zoomFrame = Frame.FrameMake((this.devWidth - device_image_width) / 2, device_image_top_margin, device_image_width, device_image_height);
                this.deviceImage.setupZoomArea(Frame.FrameMake((-this.devWidth) / 11, -((int) (((double) this.devHeight) / 3.8d)), (int) (((double) this.devWidth) + (((double) this.devWidth) / 5.9d)), (int) (((double) this.devHeight) + (((double) this.devHeight) / 2.25d))), this.zoomFrame);
                this.firstImg = (MotionImage) findViewById(R.id.firstImg);
                this.firstImg.setupPostion(Frame.FrameMake((int) (((double) (this.zoomFrame.f170x + this.zoomFrame.width)) - (((double) this.zoomFrame.f170x) / 2.5d)), this.zoomFrame.f171y - 10, 0, 0), 3.2f);
                this.firstImg.setLayoutParams(new FrameLayout.LayoutParams(this.zoomFrame.width / 4, this.zoomFrame.width / 4));
                this.secondImg = (MotionImage) findViewById(R.id.secondImg);
                this.secondImg.setupPostion(Frame.FrameMake(this.zoomFrame.f170x - (this.zoomFrame.f170x / 5), (int) (((double) this.zoomFrame.f171y) + (((double) this.zoomFrame.height) / 2.5d)), 0, 0), 3.2f);
                this.secondImg.setLayoutParams(new FrameLayout.LayoutParams((int) (((double) this.zoomFrame.width) / 3.5d), (int) (((double) this.zoomFrame.width) / 3.5d)));
                this.thirdImg = (MotionImage) findViewById(R.id.thirdImg);
                this.thirdImg.setupPostion(Frame.FrameMake((this.zoomFrame.f170x + this.zoomFrame.width) - (this.zoomFrame.f170x / 8), (int) (((double) this.zoomFrame.f171y) + (((double) this.zoomFrame.height) / 1.5d)), 0, 0), 3.2f);
                this.thirdImg.setLayoutParams(new FrameLayout.LayoutParams((int) (((double) this.zoomFrame.width) / 4.5d), (int) (((double) this.zoomFrame.width) / 4.5d)));
                this.iconList.add(this.firstImg);
                this.iconList.add(this.secondImg);
                this.iconList.add(this.thirdImg);
                this.deviceImage.moving((float) this.devWidth);
                this.scroller.addPage(new View(this));
            } else if (i == 1) {
                this.scroller.addPage(new View(this));
                this.tvSecondPage = (MotionTextView) findViewById(R.id.second_page_text);
                this.tvSecondPage.setupPostion(Frame.FrameMake(0, 100, 0, 0), 3.2f);
                this.tvSecondPage.setVisibility(View.INVISIBLE);
                this.tvSecondPage.setText(getResources().getString(R.string.startup_tutorial_page1));
            } else if (i == 2) {
                View thirdPage = layoutInflater.inflate(R.layout.start_tutorial2, null);
                this.tvThirdPage = (TextView) thirdPage.findViewById(R.id.third_page_text);
                this.tvThirdPage.setText(getResources().getString(R.string.startup_tutorial_page2));
                FrameLayout layout = (FrameLayout) thirdPage.findViewById(R.id.thirdPageLayout);
                this.thirdPageDeviceImage = (ImageViewFrame) thirdPage.findViewById(R.id.thirdPageDeviceImage);
                this.thirdPageDeviceImage.setFrame(this.zoomFrame);
                this.productImgScroll = (ScrollView) thirdPage.findViewById(R.id.productImageScroll);
                this.productImgScroll.setX((float) (((double) this.zoomFrame.f170x) + (((double) this.zoomFrame.width) / 12.9d)));
                this.productImgScroll.setY((float) (((double) this.zoomFrame.f171y) + (((double) this.zoomFrame.height) / 5.5d)));
                this.productImgScroll.setOnTouchListener(new C28522());
                ViewGroup.LayoutParams productImgScrollParams = this.productImgScroll.getLayoutParams();
                productImgScrollParams.width = (int) (((double) this.zoomFrame.width) - (((double) this.zoomFrame.width) / 7.1d));
                productImgScrollParams.height = (int) (((double) this.zoomFrame.height) - (((double) this.zoomFrame.height) / 3.14d));
                this.productImgScroll.setLayoutParams(productImgScrollParams);
                this.zoomImgSize = device_image_width / 4;
                this.firstZoomImg = (CircularImageViewFrame) thirdPage.findViewById(R.id.firstZoomIcon);
                this.firstZoomImg.setFrame(Frame.FrameMake((this.zoomFrame.f170x + this.zoomFrame.width) - (this.zoomFrame.f170x / 5), (int) (((double) this.zoomFrame.f171y) + (((double) this.zoomFrame.height) / 1.8d)), this.zoomImgSize, this.zoomImgSize));
                this.secondZoomImg = (CircularImageViewFrame) thirdPage.findViewById(R.id.secondZoomIcon);
                this.secondZoomImg.setFrame(Frame.FrameMake(this.zoomFrame.f170x - (this.zoomFrame.f170x / 5), this.zoomFrame.f171y + (this.zoomFrame.f170x / 2), this.zoomImgSize, this.zoomImgSize));
                layout.bringChildToFront(this.secondZoomImg);
                this.thirdZoomImg = (CircularImageViewFrame) thirdPage.findViewById(R.id.thirdZoomIcon);
                this.thirdZoomImg.setFrame(Frame.FrameMake((this.zoomFrame.f170x + this.zoomFrame.width) - (this.zoomFrame.f170x / 5), this.zoomFrame.f171y + (this.zoomFrame.f170x / 3), this.zoomImgSize, this.zoomImgSize));
                layout.bringChildToFront(this.thirdZoomImg);
                this.fouthZoomImg = (CircularImageViewFrame) thirdPage.findViewById(R.id.fouthZoomIcon);
                this.fouthZoomImg.setFrame(Frame.FrameMake((int) (((double) this.zoomFrame.f170x) + (((double) this.zoomFrame.width) / 2.2d)), (int) (((double) this.zoomFrame.f171y) - (((double) this.zoomImgSize) / 2.2d)), this.zoomImgSize, this.zoomImgSize));
                this.fifthZoomImg = (CircularImageViewFrame) thirdPage.findViewById(R.id.fifthZoomIcon);
                this.fifthZoomImg.setFrame(Frame.FrameMake((int) (((double) this.zoomFrame.f170x) - (((double) this.zoomFrame.f170x) / 3.5d)), (int) (((double) this.zoomFrame.f171y) + (((double) this.zoomFrame.height) / 1.5d)), this.zoomImgSize, this.zoomImgSize));
                layout.bringChildToFront(this.fifthZoomImg);
                this.zoomingListImg.add(this.firstZoomImg);
                this.zoomingListImg.add(this.secondZoomImg);
                this.zoomingListImg.add(this.thirdZoomImg);
                this.zoomingListImg.add(this.fouthZoomImg);
                this.zoomingListImg.add(this.fifthZoomImg);
                this.isStoppedZooming = true;
                autoScrollProductList();
                this.scroller.addPage(thirdPage);
            } else if (i == 3) {
                View fouthPage = layoutInflater.inflate(R.layout.start_tutorial3, null);
                this.tvFourthPage = (TextView) fouthPage.findViewById(R.id.fouth_page_text);
                this.tvFourthPage.setText(getResources().getString(R.string.startup_tutorial_page3));
                int ensureImgWidth = (int) (((double) this.devWidth) / 3.5d);
                this.ensureImg = (ImageViewFrame) fouthPage.findViewById(R.id.ensureImg);
                this.ensureImg.setFrame(Frame.FrameMake((int) (((double) this.devWidth) - (((double) ensureImgWidth) * 1.5d)), ensureImgWidth / 2, ensureImgWidth, ensureImgWidth));
                this.scroller.addPage(fouthPage);
            }
        }
        this.scroller.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageChange(Pager scroller) {
            }

            public void onPageCountChange(Pager scroller) {
            }

            public void pageScroll(int l, int t, int oldl, int oldt) {
                Log.d("pageScroll: ", l + "-" + t + "-" + oldl + "-" + oldt);
                int page = (int) Math.floor((double) (l / StartTutorialActivity.this.devWidth));
                StartTutorialActivity.this.skipButton.setText("Bỏ qua");
                Iterator it = StartTutorialActivity.this.iconList.iterator();
                while (it.hasNext()) {
                    ((MotionImage) it.next()).moving((float) (StartTutorialActivity.this.devWidth - l));
                }
                StartTutorialActivity.this.tvSecondPage.moving((float) (StartTutorialActivity.this.devWidth - l));
                if (page == 0) {
                    StartTutorialActivity.this.skipButton.setVisibility(View.INVISIBLE);
                    StartTutorialActivity.this.deviceImage.motionRate = 0.0f;
                } else if (page > 0) {
                    zoomAndMotionLayout.bringChildToFront(StartTutorialActivity.this.firstImg);
                    zoomAndMotionLayout.bringChildToFront(StartTutorialActivity.this.secondImg);
                    zoomAndMotionLayout.requestLayout();
                    StartTutorialActivity.this.skipButton.setVisibility(View.VISIBLE);
                    StartTutorialActivity.this.deviceImage.motionRate = 1.0f;
                }
                StartTutorialActivity.this.deviceImage.moving((float) (StartTutorialActivity.this.devWidth - l));
                if (page == 1) {
                    StartTutorialActivity.this.tvSecondPage.setVisibility(View.VISIBLE);
                }
                StartTutorialActivity.this.stopZoom = true;
                if (page == 2) {
                    StartTutorialActivity.this.stopZoom = false;
                    StartTutorialActivity.this.tvSecondPage.setVisibility(View.INVISIBLE);
                    if (StartTutorialActivity.this.isStoppedZooming) {
                        StartTutorialActivity.this.zoomIcon();
                        StartTutorialActivity.this.autoScrollProductList();
                    }
                }
                if (page == 3) {
                    StartTutorialActivity.this.skipButton.setText("Bắt đầu");
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    protected void onResume() {
        super.onResume();
        this.timer = new Timer();
        new Handler().postDelayed(new C28564(), 3000);
    }

    private void autoScrollProductList() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this.productImgScroll, "scrollY", new int[]{0, this.productImgScroll.getChildAt(0).getHeight() - this.productImgScroll.getHeight()}).setDuration(2000);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }

    private void zoomIcon() {
        if (this.stopZoom) {
            this.isStoppedZooming = true;
            return;
        }
        this.isStoppedZooming = false;
        if (!this.stopZoomReverse) {
            switch (this.currentZoomingIconIndex) {
                case 0:
                    this.zoomingIcon = this.firstZoomImg;
                    this.stopZoomReverse = true;
                    break;
                case 1:
                    this.zoomingIcon = this.secondZoomImg;
                    this.currentZoomingIconIndex--;
                    break;
                case 2:
                    this.zoomingIcon = this.thirdZoomImg;
                    this.currentZoomingIconIndex--;
                    break;
                case 3:
                    this.zoomingIcon = this.fouthZoomImg;
                    this.currentZoomingIconIndex--;
                    break;
                case 4:
                    this.zoomingIcon = this.fifthZoomImg;
                    this.currentZoomingIconIndex--;
                    break;
                default:
                    break;
            }
        }
        switch (this.currentZoomingIconIndex) {
            case 0:
                this.zoomingIcon = this.firstZoomImg;
                this.currentZoomingIconIndex++;
                break;
            case 1:
                this.zoomingIcon = this.secondZoomImg;
                this.currentZoomingIconIndex++;
                break;
            case 2:
                this.zoomingIcon = this.thirdZoomImg;
                this.currentZoomingIconIndex++;
                break;
            case 3:
                this.zoomingIcon = this.fouthZoomImg;
                this.currentZoomingIconIndex++;
                break;
            case 4:
                this.zoomingIcon = this.fifthZoomImg;
                this.stopZoomReverse = false;
                break;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this.zoomingIcon, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("scaleX", new float[]{1.0f, 1.5f}), PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 1.5f})});
        objectAnimator.setDuration(250);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.addListener(new C28585());
        objectAnimator.start();
    }
}
