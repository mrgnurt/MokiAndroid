package com.coho.moki.pushnotification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.ui.product.ProductChatActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> payload = remoteMessage.getData();
        String codeString = payload.get("code");
        int code = Integer.parseInt(codeString);
        Activity currActivity = BaseApp.currActivity;

        if (currActivity != null) {
            Log.d(TAG, "co activity");
        }

        // chat message
        if (code == AppConstant.CHAT_PUSH_CODE) {
            Log.d(TAG, "chat push notification");
            String messageId = payload.get(AppConstant.MESSAGE_ID_CHAT_TAG);
            String message = payload.get(AppConstant.MESSAGE_CHAT_TAG);
            String productId = payload.get(AppConstant.PRODUCT_ID_CHAT_TAG);
            String partnerId = payload.get(AppConstant.PARTNER_ID_CHAT_TAG);
            String partnerUsername = payload.get(AppConstant.PARTNER_USERNAME_CHAT_TAG);
            String partnerAvatar = payload.get(AppConstant.PARTNER_AVATAR_CHAT_TAG);

            Bundle extraData = new Bundle();
            extraData.putString(AppConstant.PRODUCT_ID_CHAT_TAG, productId);
            extraData.putString(AppConstant.PARTNER_ID_CHAT_TAG, partnerId);
            extraData.putString(AppConstant.PARTNER_USERNAME_CHAT_TAG, partnerUsername);
            extraData.putString(AppConstant.PARTNER_AVATAR_CHAT_TAG, partnerAvatar);

            showNotification(partnerUsername, message, extraData);
            return;
        }

        if (code == AppConstant.LOGOUT_PUSH_CODE) {
            AccountUntil.removeInfoAccount();
            if (currActivity != null) {
                Intent intent = new Intent("com.coho.moki.push");
                intent.putExtra("title", AppConstant.APP_NAME);
                intent.putExtra("content", AppConstant.LOGOUT_PUSH_CONTENT);
                LocalBroadcastManager.getInstance(currActivity).sendBroadcast(intent);
            }
        }
    }

    public void showNotification(String title, String content, Bundle extraData) {

        Context context = BaseApp.getContext();
        Intent intent = new Intent(context, ProductChatActivity.class);
        intent.putExtra(AppConstant.PACKAGE_TAG, extraData);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_app_name));
        builder.setSmallIcon(R.drawable.icon_app_name);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }


}