//package com.coho.moki.ui.product;
//import android.app.Application;
//
//import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
//import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;
//
///**
// * Created by ash on 3/11/16.
// */
//public class MainApplication extends Application implements IAdobeAuthClientCredentials {
//
//    private static final String CREATIVE_SDK_CLIENT_ID = "62ffae7f28144497a030e4d6174a45e0";
//    private static final String CREATIVE_SDK_CLIENT_SECRET = "0984b9ca-02ce-4e99-97bb-460f9c0f7ac5";
//    private static final String CREATIVE_SDK_REDIRECT_URI = "ams+174b205f307527e0bc9eafe52ed8442e928b4763://adobeid/62ffae7f28144497a030e4d6174a45e0";
//    private static final String[] CREATIVE_SDK_SCOPES = {"email", "profile", "address"};
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
//    }
//
//    @Override
//    public String getClientID() {
//        return CREATIVE_SDK_CLIENT_ID;
//    }
//
//    @Override
//    public String getClientSecret() {
//        return CREATIVE_SDK_CLIENT_SECRET;
//    }
//
//    @Override
//    public String[] getAdditionalScopesList() {
//        return CREATIVE_SDK_SCOPES;
//    }
//
//    @Override
//    public String getRedirectURI() {
//        return CREATIVE_SDK_REDIRECT_URI;
//    }
//}