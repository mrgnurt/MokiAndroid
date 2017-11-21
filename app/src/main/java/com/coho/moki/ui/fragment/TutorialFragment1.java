package com.coho.moki.ui.fragment;

import android.animation.Animator;
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
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.start_tutorial.CircularImageViewFrame;
import com.coho.moki.ui.start_tutorial.Frame;
import com.coho.moki.ui.start_tutorial.ImageViewFrame;
import com.coho.moki.ui.start_tutorial.MotionImage;
import com.coho.moki.ui.start_tutorial.MotionTextView;
import com.coho.moki.ui.start_tutorial.OnPageChangeListener;
import com.coho.moki.ui.start_tutorial.PageIndicator;
import com.coho.moki.ui.start_tutorial.Pager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by trung on 10/11/2017.
 */

public class TutorialFragment1 extends BaseFragment {

    private int devHeight;
    private int devWidth;
    @BindView(R.id.deviceImage)
    MotionImage deviceImage;

    @BindView(R.id.firstImg)
    MotionImage firstImg;
    private ArrayList<MotionImage> iconList = new ArrayList();


    PageIndicator indicator;

    @BindView(R.id.scrollView)
    Pager scroller;
    @BindView(R.id.secondImg)
    MotionImage secondImg;


    Button skipButton;
    private boolean stopZoom;
    private boolean stopZoomReverse;
    @BindView(R.id.thirdImg)
    MotionImage thirdImg;
    private ImageViewFrame thirdPageDeviceImage;
    private CircularImageViewFrame thirdZoomImg;
    private Timer timer;
    private TextView tvFourthPage;
    @BindView(R.id.second_page_text)
    MotionTextView tvSecondPage;
    private TextView tvThirdPage;
    private Frame zoomFrame;

    float preX;
    boolean actionUp = true;

    @BindView(R.id.layout)
    FrameLayout zoomAndMotionLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.test_tutorial1;
    }

    @Override
    protected void initView() {
        super.initView();
        Log.d("move", "fdf");
        this.skipButton = (Button) getActivity().findViewById(R.id.skip_button);
        this.skipButton.setVisibility(View.INVISIBLE);
        DisplayMetrics metrics = getActivity().getApplication().getResources().getDisplayMetrics();
        this.devWidth = metrics.widthPixels;
        this.devHeight = metrics.heightPixels;
        int device_image_top_margin = this.devHeight / 20;
        this.indicator = (PageIndicator) getActivity().findViewById(R.id.indicator);
        this.indicator.setPager(this.scroller);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        int device_image_width = this.devWidth - ((int) (((double) this.devWidth) / 1.7d));
        int device_image_height = (int) (((double) device_image_width) * 2.1d);
        Log.d("trungdeviceimage", device_image_height + "" + device_image_height);
        ViewGroup.LayoutParams indicatorParams = this.indicator.getLayoutParams();
        indicatorParams.height = this.devHeight / 25;
        this.indicator.setLayoutParams(indicatorParams);
        for (int i = 0; i < 2; i++) {
            if (i == 0) {

                this.zoomFrame = Frame.FrameMake((this.devWidth - device_image_width) / 2, device_image_top_margin, device_image_width, device_image_height);
                this.deviceImage.setupZoomArea(Frame.FrameMake((-this.devWidth) / 11, -((int) (((double) this.devHeight) / 3.8d)), (int) (((double) this.devWidth) + (((double) this.devWidth) / 5.9d)), (int) (((double) this.devHeight) + (((double) this.devHeight) / 2.25d))), this.zoomFrame);
                Log.d("trung", devWidth + " " + devHeight);
                this.firstImg.setupPostion(Frame.FrameMake((int) (((double) (this.zoomFrame.x + this.zoomFrame.width)) - (((double) this.zoomFrame.x) / 2.5d)), this.zoomFrame.y - 10, 0, 0), 3.2f);
                this.firstImg.setLayoutParams(new FrameLayout.LayoutParams(this.zoomFrame.width / 4, this.zoomFrame.width / 4));
                this.secondImg.setupPostion(Frame.FrameMake(this.zoomFrame.x - (this.zoomFrame.x / 5), (int) (((double) this.zoomFrame.y) + (((double) this.zoomFrame.height) / 2.5d)), 0, 0), 3.2f);
                this.secondImg.setLayoutParams(new FrameLayout.LayoutParams((int) (((double) this.zoomFrame.width) / 3.5d), (int) (((double) this.zoomFrame.width) / 3.5d)));
                this.thirdImg.setupPostion(Frame.FrameMake((this.zoomFrame.x + this.zoomFrame.width) - (this.zoomFrame.x / 8), (int) (((double) this.zoomFrame.y) + (((double) this.zoomFrame.height) / 1.5d)), 0, 0), 3.2f);
                this.thirdImg.setLayoutParams(new FrameLayout.LayoutParams((int) (((double) this.zoomFrame.width) / 4.5d), (int) (((double) this.zoomFrame.width) / 4.5d)));
                this.iconList.add(this.firstImg);
                this.iconList.add(this.secondImg);
                this.iconList.add(this.thirdImg);
                this.deviceImage.moving((float) this.devWidth);
                this.scroller.addPage(new View(getContext()));
            } else if (i == 1) {
                this.scroller.addPage(new View(getContext()));
                this.tvSecondPage.setupPostion(Frame.FrameMake(0, 0, 0, 0), 3.2f);
                this.tvSecondPage.setVisibility(View.INVISIBLE);
                this.tvSecondPage.setText(getResources().getString(R.string.startup_tutorial_page1));
            }
        }

        this.scroller.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageChange(Pager scroller) {
            }

            public void onPageCountChange(Pager scroller) {
            }

            public void pageScroll(int l, int t, int oldl, int oldt) {
                Log.d("trung: ", l + " " + t + " " + oldl + " " + oldt);
                final int page = (int) Math.floor((double) (l / TutorialFragment1.this.devWidth));
                TutorialFragment1.this.skipButton.setText("Bỏ qua");
                Iterator it = TutorialFragment1.this.iconList.iterator();
                while (it.hasNext()) {
                    ((MotionImage) it.next()).moving((float) (TutorialFragment1.this.devWidth - l));
                }
                TutorialFragment1.this.tvSecondPage.moving((float) (TutorialFragment1.this.devWidth - l));
                if (page == 0) {
                    TutorialFragment1.this.skipButton.setVisibility(View.INVISIBLE);
                    TutorialFragment1.this.deviceImage.motionRate = 0.0f;
                    scroller.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });
                } else if (page > 0) {
                    zoomAndMotionLayout.bringChildToFront(TutorialFragment1.this.firstImg);
                    zoomAndMotionLayout.bringChildToFront(TutorialFragment1.this.secondImg);
                    zoomAndMotionLayout.requestLayout();
                    TutorialFragment1.this.skipButton.setVisibility(View.VISIBLE);
                    TutorialFragment1.this.deviceImage.motionRate = 1.0f;
                }
                //lam muot giua cac pager cua viewpager
                TutorialFragment1.this.deviceImage.moving((float) (TutorialFragment1.this.devWidth - l));
                if (page == 1) {
                    TutorialFragment1.this.tvSecondPage.setVisibility(View.VISIBLE);
                    Log.d("move", "fdf");
                    scroller.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {

                            float x = motionEvent.getX();

                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                                preX = motionEvent.getX();
                            }

                            if (preX <= x){
                                return false;
                            }
                            else {

                                return true;
                            }
                        }
                    });
                }
                TutorialFragment1.this.stopZoom = true;
            }
        });
    }
}
