package com.coolapps.dailywater.target.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import androidx.core.app.NotificationCompat;
import com.coolapps.dailywater.target.R;
import com.coolapps.dailywater.target.helpers.NotificationHelper;
import com.coolapps.dailywater.target.utils.AppUtils;

public final class NotifierReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder nBuilder;

        SharedPreferences prefs = context.getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
        String notificationsTone = prefs.getString(AppUtils.Companion.getNOTIFICATION_TONE_URI_KEY(), RingtoneManager.getDefaultUri(2).toString());
        String title = context.getResources().getString(R.string.app_name);
        String messageToShow = prefs.getString(AppUtils.Companion.getNOTIFICATION_MSG_KEY(), context.getResources().getString(R.string.pref_notification_message_value));
        NotificationHelper nHelper = new NotificationHelper(context);
        if (messageToShow != null) {
            nBuilder = nHelper.getNotification(title, messageToShow, notificationsTone);
        } else {
            nBuilder = null;
        }
        nHelper.notify(1, nBuilder);
    }
}
