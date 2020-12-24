package com.coolapps.dailywater.target.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.coolapps.dailywater.target.recievers.BootReceiver;
import com.coolapps.dailywater.target.recievers.NotifierReceiver;
import java.util.concurrent.TimeUnit;

public final class AlarmHelper {
    private final String ACTION_BD_NOTIFICATION = "io.github.z3r0c00l_2k.aquadroid.NOTIFICATION";
    private AlarmManager alarmManager;

    public final void setAlarm(Context context, long notificationFrequency) {
        long notificationFrequencyMs = TimeUnit.MINUTES.toMillis(notificationFrequency);
        Object systemService = context.getSystemService(Context.ALARM_SERVICE);
        if (systemService != null) {
            this.alarmManager = (AlarmManager) systemService;
            Intent alarmIntent = new Intent(context, NotifierReceiver.class);
            alarmIntent.setAction(this.ACTION_BD_NOTIFICATION);
            PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 134217728);
            Log.i("AlarmHelper", "Setting Alarm Interval to: " + notificationFrequency + " minutes");
            AlarmManager alarmManager2 = this.alarmManager;
            if (alarmManager2 == null) {
            }
            alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), notificationFrequencyMs, pendingAlarmIntent);
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, BootReceiver.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            return;
        }

    }

    public final void cancelAlarm(Context context) {
        Object systemService = context.getSystemService(Context.ALARM_SERVICE);
        if (systemService != null) {
            this.alarmManager = (AlarmManager) systemService;
            Intent alarmIntent = new Intent(context, NotifierReceiver.class);
            alarmIntent.setAction(this.ACTION_BD_NOTIFICATION);
            PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 134217728);
            AlarmManager alarmManager2 = this.alarmManager;
            if (alarmManager2 == null) {
            }
            alarmManager2.cancel(pendingAlarmIntent);
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, BootReceiver.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            Log.i("AlarmHelper", "Cancelling alarms");
            return;
        }

    }

    public final boolean checkAlarm(Context context) {
        Intent alarmIntent = new Intent(context, NotifierReceiver.class);
        alarmIntent.setAction(this.ACTION_BD_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 0, alarmIntent, 536870912) != null;
    }
}
