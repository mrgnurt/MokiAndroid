package com.coho.moki.ui.product;

import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.coho.moki.ui.base.BaseActivity;

import com.coho.moki.R;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 11/8/2017.
 */

public class CameraActivity extends BaseActivity {

    @BindView(R.id.preview)
    FrameLayout preview;

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;

    @BindView(R.id.cameraAction)
    RelativeLayout cameraAction;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.btnSwitch)
    Button btnSwitch;

    @BindView(R.id.btnFlash)
    Button btnFlash;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;

    @BindView(R.id.rlSwitchMode)
    RelativeLayout rlSwitchMode;

    @BindView(R.id.btnPhoto)
    Button btnPhoto;

    @BindView(R.id.btnCamera)
    Button btnCamera;

    @BindView(R.id.imgCapturePhoto)
    ImageView imgCapturePhoto;

    @BindView(R.id.imgCaptureVideo)
    ImageView imgCaptureVideo;

    @Override
    public int setContentViewId() {
        return R.layout.camera_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
