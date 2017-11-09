package com.coho.moki.ui.product;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.coho.moki.ui.base.BaseActivity;

import com.coho.moki.R;
import com.coho.moki.ui.custom.CameraView;

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

    private boolean isCapturePhoto = true;

    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private int currentCameraType;
    private boolean isSupportFlash;
    private boolean isFlashOn;

    @Override
    public int setContentViewId() {
        return R.layout.camera_activity;
    }

    @Override
    public void initView() {
        try {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);//you can use open(int) to use different cameras
            currentCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
            Log.d(TAG, "open camera success");
        } catch (Exception e) {
            Log.d(TAG, "Failed to get camera: " + e.getMessage());
        }

        if (mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            preview.addView(mCameraView);//add the SurfaceView to the layout
        }
        isSupportFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isSupportFlash) {
            btnFlash.setVisibility(View.GONE);
        } else {
            btnFlash.setVisibility(View.VISIBLE);
            isFlashOn = false;
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

    @OnClick(R.id.btnSwitch)
    public void onClickButtonSwitch() {
        // code to destroy surfaceview
        if (mCamera != null) {
            mCameraView.surfaceDestroyed(mCameraView.getHolder());
            mCameraView.getHolder().removeCallback(mCameraView);
            mCameraView.destroyDrawingCache();
            preview.removeView(mCameraView);
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            if (Camera.getNumberOfCameras() < currentCameraType) {
                currentCameraType = Camera.CameraInfo.CAMERA_FACING_BACK;
            }
            try {
                mCamera.open(currentCameraType);
                Log.d(TAG, "Open camera success");
            } catch (Exception e) {
                Log.d(TAG, "Failed to get camera: " + e.getMessage());
            }
            if (mCamera != null) {
                mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
                preview.addView(mCameraView);//add the SurfaceView to the layout
            }
        }
    }

    @OnClick(R.id.btnFlash)
    public void onClickButtonFlash() {
        if (isSupportFlash && mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            if (params != null && !isFlashOn) {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                // need create new instance of camera and surfaceview?
                mCamera.setParameters(params);
                mCamera.startPreview();
            } else {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                mCamera.startPreview();
            }
        }
    }

}
