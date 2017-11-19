package com.coho.moki.ui.fragment;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.model.Image;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.util.AccountUntil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by trung on 19/11/2017.
 */

public class IntroTutFragment extends BaseFragment {

    @BindView(R.id.slide1)
    LinearLayout mSlide1;

    @BindView(R.id.swipeImageView)
    ImageView mSwipeImageView;

    @BindView(R.id.slide2)
    RelativeLayout mSlide2;

    @BindView(R.id.changeModeBtn)
    Button mChangeModeBtn;

    @BindView(R.id.arrowChangeMode)
    ImageView mImgChangeMode;

    @BindView(R.id.slide3)
    RelativeLayout mSlide3;

    @BindView(R.id.arrowCamera)
    ImageView mImgArrowCamera;

    @BindView(R.id.btnCamera)
    Button mBtnCamera;

    @OnClick(R.id.btnClose)
    public void onClickBtnClose(){

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_intro_tutorial;
    }

    @Override
    protected void initView() {

        mSwipeImageView.startAnimation(AnimationUtils.loadAnimation(BaseApp.getContext(), R.anim.move_to_right));
        mImgChangeMode.startAnimation(AnimationUtils.loadAnimation(BaseApp.getContext(), R.anim.move_top_right));
        mImgArrowCamera.startAnimation(AnimationUtils.loadAnimation(BaseApp.getContext(), R.anim.move_bottom_right));
        mSlide2.setVisibility(View.GONE);
        mSlide3.setVisibility(View.GONE);

        mSlide1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    mSlide1.setVisibility(View.GONE);
                    mSlide2.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        mChangeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlide2.setVisibility(View.GONE);
                mSlide3.setVisibility(View.VISIBLE);
            }
        });

        mBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountUntil.passIntroTutFRAGMENT();
                getActivity().getSupportFragmentManager().beginTransaction().remove(IntroTutFragment.this).commit();
            }
        });

    }
}
