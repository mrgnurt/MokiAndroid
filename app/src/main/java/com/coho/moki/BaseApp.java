package com.coho.moki;

import android.app.Application;
import android.content.Context;

import com.coho.moki.di.component.ActivityComponent;
import com.coho.moki.di.component.DaggerActivityComponent;
import com.coho.moki.di.component.DaggerServiceComponent;
import com.coho.moki.di.component.ServiceComponent;
import com.coho.moki.di.module.ActivityModule;
import com.coho.moki.di.module.ServiceModule;
import com.coho.moki.util.SharedPrefUtils;

/**
 * Created by trung on 21/09/2017.
 */

public class BaseApp extends Application {

    private static Context mContext;
    private static SharedPrefUtils mSharedPreferences;
    private static ActivityComponent mActivityComponent;
    private static ServiceComponent mServiceComponent;

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
}
