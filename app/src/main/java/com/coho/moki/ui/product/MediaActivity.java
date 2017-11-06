package com.coho.moki.ui.product;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.coho.moki.R;
import com.coho.moki.adapter.product.ImagePagerAdapter;
import com.coho.moki.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/18/2017.
 */

public class MediaActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.videoView)
    VideoView videoView;

    @BindView(R.id.loading_icon)
    ProgressBar pbLoading;

    @BindView(R.id.imgCancel)
    Button btnCancel;

    private List<String> imgList;

    @Override
    public int setContentViewId() {
        return R.layout.activity_media;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        // set list image
        Intent intent = getIntent();
        imgList = intent.getStringArrayListExtra("listImage");
        initImage();
    }

    private void initVideo() {

    }

    private void initImage() {
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setAdapter(new ImagePagerAdapter(MediaActivity.this, imgList));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
