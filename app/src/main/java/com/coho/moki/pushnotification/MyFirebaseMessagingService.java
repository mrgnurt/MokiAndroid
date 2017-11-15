package com.coho.moki.pushnotification;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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

        // logout
        if (code == 1) {
            AccountUntil.removeInfoAccount();

            if (currActivity != null) {

                Log.d("quangtu", "Ssss");
                Intent intent = new Intent("com.coho.moki.push");
                intent.putExtra("title", "ssss");
                intent.putExtra("content", "sss");

                LocalBroadcastManager.getInstance(currActivity).sendBroadcast(intent);
            }
        }

        Log.d("code_noti", codeString);
        Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        Log.d(TAG, "Message data from: " + remoteMessage.getFrom());
    }

}