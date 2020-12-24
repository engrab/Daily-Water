package com.coolapps.dailywater.target.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.coolapps.dailywater.target.helpers.AlarmHelper;
import com.coolapps.dailywater.target.utils.AppUtils;

public final class BootReceiver extends BroadcastReceiver {
    private final AlarmHelper alarm = new AlarmHelper();

    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null ) {
            if (context == null) {

            }
            SharedPreferences prefs = context.getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
            int notificationFrequency = prefs.getInt(AppUtils.Companion.getNOTIFICATION_FREQUENCY_KEY(), 60);
            boolean notificationsNewMessage = prefs.getBoolean("notifications_new_message", true);
            this.alarm.cancelAlarm(context);
            if (notificationsNewMessage) {
                this.alarm.setAlarm(context, (long) notificationFrequency);
            }
        }
    }
}
