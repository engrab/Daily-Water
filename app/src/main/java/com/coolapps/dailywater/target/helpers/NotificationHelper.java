package com.coolapps.dailywater.target.helpers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.coolapps.dailywater.target.R;
import com.coolapps.dailywater.target.MainActivity;
import com.coolapps.dailywater.target.utils.AppUtils;
import java.util.Calendar;
import java.util.Date;

public final class NotificationHelper {
    private final String CHANNEL_ONE_ID = "io.github.z3r0c00l_2k.aquadroid.CHANNELONE";
    private final String CHANNEL_ONE_NAME = "Channel One";
    private final Context ctx;
    private NotificationManager notificationManager;

    public NotificationHelper(Context ctx2) {
        this.ctx = ctx2;
    }

    public final Context getCtx() {
        return this.ctx;
    }

    private final void createChannels() {
        if (Build.VERSION.SDK_INT >= 26) {
            String notificationsNewMessageRingtone = this.ctx.getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE()).getString(AppUtils.Companion.getNOTIFICATION_TONE_URI_KEY(), RingtoneManager.getDefaultUri(2).toString());
            NotificationChannel notificationChannel = new NotificationChannel(this.CHANNEL_ONE_ID, this.CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            boolean z = true;
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-16776961);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(1);
            if (notificationsNewMessageRingtone == null) {
            }
            if (notificationsNewMessageRingtone.length() <= 0) {
                z = false;
            }
            if (z) {
                notificationChannel.setSound(Uri.parse(notificationsNewMessageRingtone), new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_NOTIFICATION).build());
            }
            NotificationManager manager = getManager();
            if (manager == null) {
            }
            manager.createNotificationChannel(notificationChannel);
        }
    }

    public final NotificationCompat.Builder getNotification(String title, String body, String notificationsTone) {

        createChannels();
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this.ctx.getApplicationContext(), this.CHANNEL_ONE_ID).setContentTitle(title).setContentText(body).setLargeIcon(BitmapFactory.decodeResource(this.ctx.getResources(), R.mipmap.ic_launcher)).setSmallIcon(R.drawable.ic_small_logo).setAutoCancel(true);
        notification.setShowWhen(true);
        notification.setSound(Uri.parse(notificationsTone));
        Intent notificationIntent = new Intent(this.ctx, MainActivity.class);
        notificationIntent.setFlags(603979776);
        notification.setContentIntent(PendingIntent.getActivity(this.ctx, 99, notificationIntent, 134217728));
        return notification;
    }

    private final boolean shallNotify() {
        SharedPreferences prefs = this.ctx.getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
        SqliteHelper sqliteHelper = new SqliteHelper(this.ctx);
        String currentDate = AppUtils.Companion.getCurrentDate();
        if (currentDate == null) {

        }
        int percent = (sqliteHelper.getIntook(currentDate) * 100) / prefs.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0);
        boolean doNotDisturbOff = true;
        long startTimestamp = prefs.getLong(AppUtils.Companion.getWAKEUP_TIME(), 0);
        long stopTimestamp = prefs.getLong(AppUtils.Companion.getSLEEPING_TIME_KEY(), 0);
        if (startTimestamp > 0 && stopTimestamp > 0) {
            Calendar instance = Calendar.getInstance();

            Date now = instance.getTime();
            Date start = new Date(startTimestamp);
            Date stop = new Date(stopTimestamp);
            doNotDisturbOff = compareTimes(now, start) >= 0 && compareTimes(now, stop) <= 0;
        }
        return doNotDisturbOff && percent < 100;
    }

    private final long compareTimes(Date currentTime, Date timeToRun) {
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentTime);
        Calendar runCal = Calendar.getInstance();
        runCal.setTime(timeToRun);
        runCal.set(5, currentCal.get(5));
        runCal.set(2, currentCal.get(2));
        runCal.set(1, currentCal.get(1));
        return currentCal.getTimeInMillis() - runCal.getTimeInMillis();
    }

    public final void notify(long id, NotificationCompat.Builder notification) {
        if (shallNotify()) {
            NotificationManager manager = getManager();
            if (manager == null) {
            }
            int i = (int) id;
            if (notification == null) {
            }
            manager.notify(i, notification.build());
            return;
        }
        Log.i("AquaDroid", "dnd period");
    }

    private final NotificationManager getManager() {
        if (this.notificationManager == null) {
            Object systemService = this.ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            if (systemService != null) {
                this.notificationManager = (NotificationManager) systemService;
            } else {
            }
        }
        return this.notificationManager;
    }
}
