package com.coho.moki.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.di.component.ActivityComponent;
import com.coho.moki.di.module.ActivityModule;
import com.coho.moki.util.DebugLog;
import com.coho.moki.util.DialogUtil;

import butterknife.ButterKnife;

/**
 * Created by trung on 22/09/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        onPreSetContentView(savedInstanceState);
        super.onCreate(savedInstanceState);
        onPostSetContentView(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            handleDeepLinkData(intent.getData());
        }
        setContentView(setContentViewId());
        ButterKnife.bind(this);
        initPresenter();
        handleIntent(intent);
        BaseApp.currActivity = this;
        initView();
        initData();

//        registerLocalBroadcast();
    }

    BroadcastReceiver receiver;
    /**
     * Handle data before setContentView call
     *
     * @param savedInstanceState
     */
    protected void onPreSetContentView(Bundle savedInstanceState) {

    }

    /**
     * Handle data after setContentView call
     *
     * @param savedInstanceState
     */
    protected void onPostSetContentView(Bundle savedInstanceState) {

    }

    /**
     * Handle deep link data
     *
     * @param uri
     */
    protected void handleDeepLinkData(Uri uri) {
        DebugLog.i("uri: " + uri.toString());
    }

    /**
     * @return layout of activity
     */
    public abstract int setContentViewId();

    /**
     * Define your view
     */
    public abstract void initView();

    /**
     * Setup your data
     */
    public abstract void initData();

    /**
     * Handle intent
     * @param intent
     */
    public void handleIntent(Intent intent) {}

    public void initPresenter(){}

    public void createCustomTitleBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.nav_bar_custom);
        View view = getSupportActionBar().getCustomView();
    }

//    public void registerLocalBroadcast() {
//        receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                try {
//
//                    Log.d("onReceive", "vao day");
//                    String title= intent.getStringExtra("title");
//                    String content= intent.getStringExtra("content");
//                    int type = intent.getIntExtra("type", 2);
//
//                    DialogUtil.showPopup(BaseActivity.this, content, title);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("com.coho.moki.push"));
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        registerLocalBroadcast();
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
//    }
}
