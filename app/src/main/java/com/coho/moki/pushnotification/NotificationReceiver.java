package com.coho.moki.pushnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.ui.login.LoginActivity;
import com.coho.moki.ui.product.ProductChatActivity;
import com.coho.moki.util.AccountUntil;

import java.nio.channels.AcceptPendingException;

/**
 * Created by tonquangtu on 17/11/2017.
 */
public class NotificationReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String codeString = intent.getStringExtra("code");
        int code = Integer.parseInt(codeString);
        playNotificationSound(context, code);
        if (code == AppConstant.LOGOUT_PUSH_CODE) {
            AccountUntil.removeInfoAccount();
            openLoginActivity(context, intent);
        }
    }

    public void playNotificationSound(Context context, int code) {
        try {
            Uri soundUri;
            if (code == AppConstant.CHAT_PUSH_CODE) {
                soundUri = Uri.parse("android.resource://"
                        + context.getPackageName() + "/" + R.raw.new_message);
            } else if (code == AppConstant.LOGOUT_PUSH_CODE) {
                soundUri = Uri.parse("android.resource://"
                        + context.getPackageName() + "/" + R.raw.other_device_login);
            } else {
                soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            Ringtone r = RingtoneManager.getRingtone(context, soundUri);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openLoginActivity(Context context, Intent intent) {
        Intent loginIntent = new Intent(context, LoginActivity.class);
//        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        loginIntent.putExtra(AppConstant.LOGIN_TYPE_TAG, AppConstant.OTHER_PEOPLE_LOGIN);
        context.startActivity(loginIntent);
        if (BaseApp.currActivity != null) {
            BaseApp.currActivity.finish();
        }
    }

//    public void showLogoutPopup(Context context) {
//        Intent intent = new Intent("com.coho.moki.push");
//        intent.putExtra(AppConstant.TITLE_PUSH_TAG, AppConstant.APP_NAME);
//        intent.putExtra(AppConstant.CONTENT_PUSH_TAG, AppConstant.LOGOUT_PUSH_CONTENT);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//    }
}