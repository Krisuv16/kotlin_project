package com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.kb.mapd721_p1_krisuvbohara_sarthakvasistha.view.MainFormActivity;

public class NotificationDecorator {

        private static final String TAG = "NotificationDecorator";
        private final Context context;
        private final NotificationManager notificationMgr;

        public NotificationDecorator(Context context, NotificationManager notificationManager) {
            this.context = context;
            this.notificationMgr = notificationManager;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void displaySimpleNotification(String title, String contentText) {
            Intent intent = new Intent(context, MainFormActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            try {
                notificationMgr.createNotificationChannel(new NotificationChannel("channelId", "Default channel", NotificationManager.IMPORTANCE_HIGH));
                notificationMgr.notify(0,  new NotificationCompat.Builder(context, "channelId")
                        .setSmallIcon(android.R.drawable.ic_btn_speak_now, 10)
                        .setContentTitle("Notification")
                        .setContentText("This is a notification for you").build());
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
