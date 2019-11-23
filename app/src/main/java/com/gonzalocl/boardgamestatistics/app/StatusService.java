package com.gonzalocl.boardgamestatistics.app;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.gonzalocl.boardgamestatistics.ui.StatusNotification;

public class StatusService extends IntentService {


    public StatusService() {
        super("StatusService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        StatusNotification statusNotification = StatusNotification.getStatusNotification(this);

        startForeground(statusNotification.getNotificationId(), statusNotification.getNotification());

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            statusNotification.updateNotification(String.format("[%d]", i));

        }


    }
}
