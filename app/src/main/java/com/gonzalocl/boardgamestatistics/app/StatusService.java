package com.gonzalocl.boardgamestatistics.app;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.gonzalocl.boardgamestatistics.R;
import com.gonzalocl.boardgamestatistics.ui.StatusNotification;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Semaphore;

public class StatusService extends IntentService {

    private static Semaphore confirmedResults;

    public StatusService() {
        super("StatusService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        confirmedResults = new Semaphore(0);

        StatusNotification statusNotification = StatusNotification.getStatusNotification(this);
        BoardGameStatistics boardGameStatistics = BoardGameStatistics.getBoardGameStatistics();

        startForeground(statusNotification.getNotificationId(), statusNotification.getNotification());

        while (boardGameStatistics.getCurrentState() == BoardGameStatistics.STATE_PLAYING) {

            long now = new Date().getTime()/1000;
            long startTime = boardGameStatistics.getStartTime()/1000;
            long elapsed = now-startTime;

            long hours = elapsed/3600%60;
            long minutes = elapsed/60%60;
            long seconds = elapsed%60;

            // TODO
            String players = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                players = String.join(", ", boardGameStatistics.getCurrentPlayers());
            }

            String notificationText = String.format(Locale.getDefault(), getString(R.string.notification_format),
                    boardGameStatistics.getCurrentGameName(),
                    hours,
                    minutes,
                    seconds,
                    boardGameStatistics.getCurrentLocation(),
                    players);

            statusNotification.updateNotification(notificationText);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

        long startTime = boardGameStatistics.getStartTime()/1000;
        long endTime = boardGameStatistics.getEndTime()/1000;
        long elapsed = endTime-startTime;

        long hours = elapsed/3600%60;
        long minutes = elapsed/60%60;
        long seconds = elapsed%60;

        String notificationText = String.format(Locale.getDefault(), getString(R.string.notification_end_format),
                boardGameStatistics.getCurrentGameName(),
                hours,
                minutes,
                seconds);

        statusNotification.updateNotification(notificationText);

        try {
            confirmedResults.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void confirmResults() {
        confirmedResults.release();
    }

}
