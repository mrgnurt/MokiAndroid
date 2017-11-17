package com.coho.moki.ui.product.add;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

//import com.adobe.creativesdk.aviary.AdobeImageIntent;
import com.coho.moki.callback.OnTakePhotoListener;
import com.coho.moki.callback.PhotoHandler;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.ui.base.BaseActivity;

import com.coho.moki.R;
import com.coho.moki.ui.custom.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.tungdx.mediapicker.MediaItem;
import vn.tungdx.mediapicker.MediaOptions;
import vn.tungdx.mediapicker.activities.MediaPickerActivity;

/**
 * Created by Khanh Nguyen on 11/8/2017.
 */

@SuppressWarnings("deprecation")
public class CameraActivity extends BaseActivity {

    private final String TAG = "CameraActivity";

    private static final int REQ_CODE_CSDK_IMAGE_EDITOR = 3001;
    private static final int REQ_CODE_GALLERY_PICKER = 20;
    private static final int REQUEST_MEDIA = 100;
    private static final int REQUEST_CAMERA = 1888; // field

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

    private Camera mCamera;
    private CameraView mCameraView;
    private boolean isFlashOn;
    private boolean isSupportFlash;
    private boolean isCameraFront;

    private Integer imgPos;

    @Override
    public int setContentViewId() {
        return R.layout.camera_activity;
    }

    @Override
    public void initView() {
        try {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);//you can use open(int) to use different cameras
            if (mCamera != null) {
                mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
                preview.addView(mCameraView);//add the SurfaceView to the layout
            }
            Log.d(TAG, "open camera success");
        } catch (Exception e) {
            Log.d(TAG, "Failed to get camera: " + e.getMessage());
        }
        isSupportFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isSupportFlash) {
            btnFlash.setVisibility(View.GONE);
        } else {
            btnFlash.setVisibility(View.VISIBLE);
            isFlashOn = false;
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            btnSwitch.setVisibility(View.GONE);
        } else {
            btnSwitch.setVisibility(View.VISIBLE);
            isCameraFront = false;
        }

        // get data from AddProductActivity
        Intent intent = getIntent();
        imgPos = intent.getIntExtra(AppConstant.ADD_PRODUCT_IMG_POS, 0);

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
        destroyCamera();
//        if (isSupportFlash) {
//            if (isCameraFront) {
//                btnFlash.setVisibility(View.GONE);
//            } else {
//                btnFlash.setVisibility(View.VISIBLE);
//            }
//        }
        int type = isCameraFront ? Camera.CameraInfo.CAMERA_FACING_BACK : Camera.CameraInfo.CAMERA_FACING_FRONT;
        isCameraFront = !isCameraFront;
        try {
            mCamera = Camera.open(type);
            if (mCamera != null) {
                mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
                preview.addView(mCameraView);//add the SurfaceView to the layout
            }
            Log.d(TAG, "Open camera success");
        } catch (Exception e) {
            Log.d(TAG, "Failed to get camera: " + e.getMessage());
        }
    }

    private void destroyCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCameraView.surfaceDestroyed(mCameraView.getHolder());
            mCameraView.getHolder().removeCallback(mCameraView);
            mCameraView.destroyDrawingCache();
            preview.removeView(mCameraView);
//            //mCamera.stopPreview();
//            //mCamera.release();
            mCamera = null;

//            mCamera.stopPreview();
//            mCamera.setPreviewCallback(null);
//            mCamera.release();
//            mCamera = null;

        }
    }

    @OnClick(R.id.btnFlash)
    public void onClickButtonFlash() {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            if (params == null) {
                return;
            }
            if (!isFlashOn) {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                // need create new instance of camera and surfaceview?
                mCamera.setParameters(params);
                mCamera.startPreview();
                isFlashOn = true;
            } else {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                mCamera.startPreview();
                isFlashOn = false;
            }
        }
    }

    @OnClick(R.id.imgPhoto)
    public void openMediaPicker() {
        MediaOptions.Builder builder = new MediaOptions.Builder();
        MediaOptions options = builder.canSelectBothPhotoVideo().build();
        MediaPickerActivity.open(this, REQUEST_MEDIA, options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri;
            Intent intent;
            switch (requestCode) {
                case REQUEST_CAMERA:
//                    Bitmap picture = (Bitmap) data.getExtras().get("data");//this is your bitmap image and now you can do whatever you want with this
//                    uri = saveImage(picture);
                    uri = data.getParcelableExtra(AppConstant.ADD_PRODUCT_IMG);
                    Log.d(TAG, "uri img = " + uri);
                    if (imgPos == 0) {
                        intent = new Intent(CameraActivity.this, AddProductActivity.class);
                        intent.putExtra(AppConstant.ADD_PRODUCT_IMG, uri);
                        intent.putExtra(AppConstant.ADD_PRODUCT_IMG_POS, 0);
                        startActivity(intent);
                    } else {
                        intent = new Intent();
                        intent.putExtra(AppConstant.ADD_PRODUCT_IMG, uri);
                        intent.putExtra(AppConstant.ADD_PRODUCT_IMG_POS, imgPos);
                        setResult(Activity.RESULT_OK, intent);
                    }
                    finish();
                    break;
                case REQUEST_MEDIA:
                    List<MediaItem> mediaSelectedList = MediaPickerActivity.getMediaItemSelected(data);
                    if (mediaSelectedList != null && !mediaSelectedList.isEmpty()) {
                        MediaItem item = mediaSelectedList.get(0);
                        uri = item.getUriOrigin();
                    /* 1) Create a new Intent */
                        Log.d(TAG, "uri = " + uri.getPath());
                        if (uri != null) {
                            if (imgPos == 0) {
                                intent = new Intent(CameraActivity.this, AddProductActivity.class);
                                intent.putExtra(AppConstant.ADD_PRODUCT_IMG, uri);
                                intent.putExtra(AppConstant.ADD_PRODUCT_IMG_POS, 0);
                                startActivity(intent);
                            } else {
                                intent = new Intent();
                                intent.putExtra(AppConstant.ADD_PRODUCT_IMG, uri);
                                intent.putExtra(AppConstant.ADD_PRODUCT_IMG_POS, imgPos);
                                setResult(Activity.RESULT_OK, intent);
                            }
                            // finish camera activity
                            finish();
//                        Intent imageEditorIntent = new AdobeImageIntent.Builder(CameraActivity.this)
//                                .setData(uri) // Set in onActivityResult()
//                                .build();
////                        /* 2) Start the Image Editor with request code 1 */
//                        startActivityForResult(imageEditorIntent, REQ_CODE_CSDK_IMAGE_EDITOR);
                        }
                    }
                    break;
                case REQ_CODE_CSDK_IMAGE_EDITOR:

                    break;
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        destroyCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        destroyCamera();
//        finish();
    }

    @OnClick(R.id.imgCapturePhoto)
    public void onClickCapturePhoto() {
//        takePicture();

        Log.d(TAG, "click take pictrue");
        if (mCamera != null) {
            mCamera.takePicture(null, null, new PhotoHandler(this, new OnTakePhotoListener() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d(TAG, "take picture success");
                    Intent intent = new Intent();
                    intent.putExtra(AppConstant.ADD_PRODUCT_IMG, uri);
                    onActivityResult(REQUEST_CAMERA, Activity.RESULT_OK, intent);
                }
            }));
        }

    }

    private void takePicture() { //you can call this every 5 seconds using a timer or whenever you want
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private Uri saveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
        String date = dateFormat.format(new Date());
        String fname = "Image-" + date + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return Uri.fromFile(file);
//            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}