package com.coho.moki.ui.product;

import android.hardware.Camera;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.coho.moki.ui.base.BaseActivity;

import com.coho.moki.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/8/2017.
 */

public class CameraActivity extends BaseActivity {

    private final String TAG = "CameraActivity";

    @BindView(R.id.preview)
    FrameLayout preview;

//    @BindView(R.id.surfaceView)
//    SurfaceView surfaceView;

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

    @BindView(R.id.btnVideo)
    Button btnCamera;

    @BindView(R.id.imgCapturePhoto)
    ImageView imgCapturePhoto;

    @BindView(R.id.imgCaptureVideo)
    ImageView imgCaptureVideo;

    private boolean isCapturePhoto;

    private Camera mCamera = null;
    private CameraView mCameraView = null;

    @Override
    public int setContentViewId() {
        return R.layout.camera_activity;
    }

    @Override
    public void initView() {
        try{
            mCamera = Camera.open();//you can use open(int) to use different cameras
            Log.d(TAG, "open camera success");
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if(mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            preview.addView(mCameraView);//add the SurfaceView to the layout
        }
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.rlSwitchMode)
    public void changeCapture() {
        if (isCapturePhoto) {
            changeToVideo();
        } else {
            changeToPhoto();
        }
    }

    @OnClick(R.id.btnPhoto)
    public void onClickButtonPhoto() {
        if (!isCapturePhoto) {
            changeToPhoto();
        }
    }

    @OnClick(R.id.btnVideo)
    public void onClickButtonVideo() {
        if (isCapturePhoto) {
            changeToVideo();
        }
    }

    private void changeToPhoto() {
        rlSwitchMode.setGravity(Gravity.LEFT);
        imgCaptureVideo.setVisibility(View.GONE);
        imgCapturePhoto.setVisibility(View.VISIBLE);
        isCapturePhoto = true;
    }

    private void changeToVideo() {
        rlSwitchMode.setGravity(Gravity.RIGHT);
        imgCapturePhoto.setVisibility(View.GONE);
        imgCaptureVideo.setVisibility(View.VISIBLE);
        isCapturePhoto = false;
    }

    @OnClick(R.id.btnCancel)
    public void onClickButtonCancel() {
        onBackPressed();
    }

}
