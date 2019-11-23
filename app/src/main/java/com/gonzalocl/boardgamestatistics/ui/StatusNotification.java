package com.gonzalocl.boardgamestatistics.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.gonzalocl.boardgamestatistics.R;

public class StatusNotification {

    private static final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    private static StatusNotification statusNotification = null;

    private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    private StatusNotification(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        notificationManager = NotificationManagerCompat.from(context);

        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    context.getString(R.string.status_notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(context.getString(R.string.status_notification_channel_description));
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    public static StatusNotification getStatusNotification(Context context) {
        if (statusNotification == null) {
            statusNotification =  new StatusNotification(context);
        }
        return statusNotification;
    }

    public void updateNotification(String text) {
        notificationBuilder.setContentText(text);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public Notification getNotification() {
        return notificationBuilder.build();
    }


    public int getNotificationId() {
        return NOTIFICATION_ID;
    }


}
