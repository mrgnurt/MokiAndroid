package com.coho.moki;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;
import com.coho.moki.di.component.ActivityComponent;
import com.coho.moki.di.component.DaggerActivityComponent;
import com.coho.moki.di.component.DaggerServiceComponent;
import com.coho.moki.di.component.ServiceComponent;
import com.coho.moki.di.module.ActivityModule;
import com.coho.moki.di.module.ServiceModule;
import com.coho.moki.util.SharedPrefUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by trung on 21/09/2017.
 */

public class BaseApp extends Application implements IAdobeAuthClientCredentials {

    private static Context mContext;
    private static SharedPrefUtils mSharedPreferences;
    private static ActivityComponent mActivityComponent;
    private static ServiceComponent mServiceComponent;
    private static final String CREATIVE_SDK_CLIENT_ID = "62ffae7f28144497a030e4d6174a45e0";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "0984b9ca-02ce-4e99-97bb-460f9c0f7ac5";
    private static final String CREATIVE_SDK_REDIRECT_URI = "ams+174b205f307527e0bc9eafe52ed8442e928b4763://adobeid/62ffae7f28144497a030e4d6174a45e0";
    private static final String[] CREATIVE_SDK_SCOPES = {"email", "profile", "address"};

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mSharedPreferences = new SharedPrefUtils(mContext);

        mServiceComponent = DaggerServiceComponent
                .builder()
                .serviceModule(new ServiceModule())
                .build();

        mActivityComponent = DaggerActivityComponent
                .builder()
                .serviceComponent(mServiceComponent)
                .activityModule(new ActivityModule())
                .build();

        setDefaultRefreshLayout();

        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());


    }

    private void setDefaultRefreshLayout(){
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
//                classicsHeader.getTitleText().setText("loading");
                ClassicsHeader.REFRESH_HEADER_PULLDOWN = "pull";
                ClassicsHeader.REFRESH_HEADER_LOADING = "load";
                ClassicsHeader.REFRESH_HEADER_REFRESHING = "refresh";
                ClassicsHeader.REFRESH_HEADER_RELEASE = "relase";
                ClassicsHeader.REFRESH_HEADER_FINISH = "finish";
//                ClassicsHeader.REFRESH_HEADER_LASTTIME = "last";
                ClassicsHeader.REFRESH_HEADER_FAILED = "fails";
                classicsHeader.setTimeFormat(new SimpleDateFormat("M-d HH:mm", Locale.CHINA));
                return classicsHeader;
            }
        });
    }

    public static BaseApp get(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    public static ActivityComponent getActivityComponent(){
        return mActivityComponent;
    }

    public static ServiceComponent getServiceComponent(){
        return mServiceComponent;
    }

    public static SharedPrefUtils getSharedPreferences(){
        return mSharedPreferences;
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

    @Override
    public String[] getAdditionalScopesList() {
        return CREATIVE_SDK_SCOPES;
    }

    @Override
    public String getRedirectURI() {
        return CREATIVE_SDK_REDIRECT_URI;
    }

}
