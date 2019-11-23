package com.gonzalocl.boardgamestatistics.app;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.gonzalocl.boardgamestatistics.ui.StatusNotification;

public class StatusService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public StatusService(String name) {
        super(name);
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
