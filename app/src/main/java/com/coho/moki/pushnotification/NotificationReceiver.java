package com.coho.moki.pushnotification;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;

/**
 * Created by tonquangtu on 17/11/2017.
 */
public class NotificationReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        playNotificationSound(context, intent);
    }

    public void playNotificationSound(Context context, Intent intent) {
        try {
            Uri soundUri;
            int code = intent.getIntExtra("code", AppConstant.CHAT_PUSH_CODE);
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
}