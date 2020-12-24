package com.coolapps.dailywater.target.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public final class AppUtils {
    public static final Companion Companion = new Companion( null);

    public static final String FIRST_RUN_KEY = "FIRST_RUN_KEY";

    public static final String NOTIFICATION_FREQUENCY_KEY = "NOTIFICATION_FREQUENCY_KEY";

    public static final String NOTIFICATION_MSG_KEY = "NOTIFICATION_MSG_KEY";

    public static final String NOTIFICATION_STATUS_KEY = "NOTIFICATION_STATUS_KEY";

    public static final String NOTIFICATION_TONE_URI_KEY = "NOTIFICATION_TONE_URI_KEY";

    public static final int PRIVATE_MODE = 0;

    public static final String SLEEPING_TIME_KEY = "SLEEPING_TIME_KEY";

    public static final String TOTAL_INTAKE = "TOTAL_INTAKE";

    public static final String USERS_SHARED_PREF = "USERS_SHARED_PREF";

    public static final String WAKEUP_TIME = "WAKEUP_TIME";

    public static final String WEIGHT_KEY = "WEIGHT_KEY";

    public static final String WORK_TIME_KEY = "WORK_TIME_KEY";


    public static final class Companion {
        private Companion() {
        }

        public  Companion(Context context) {
            this();
        }

        public final double calculateIntake(int weight, int workTime) {
            return (((double) (weight * 100)) / 3.0d) + ((double) ((workTime / 6) * 7));
        }

        public final String getCurrentDate() {
            Calendar instance = Calendar.getInstance();
            return new SimpleDateFormat("dd-MM-yyyy").format(instance.getTime());
        }

        public final String getUSERS_SHARED_PREF() {
            return AppUtils.USERS_SHARED_PREF;
        }

        public final int getPRIVATE_MODE() {
            return AppUtils.PRIVATE_MODE;
        }

        public final String getWEIGHT_KEY() {
            return AppUtils.WEIGHT_KEY;
        }

        public final String getWORK_TIME_KEY() {
            return AppUtils.WORK_TIME_KEY;
        }

        public final String getTOTAL_INTAKE() {
            return AppUtils.TOTAL_INTAKE;
        }

        public final String getNOTIFICATION_STATUS_KEY() {
            return AppUtils.NOTIFICATION_STATUS_KEY;
        }

        public final String getNOTIFICATION_FREQUENCY_KEY() {
            return AppUtils.NOTIFICATION_FREQUENCY_KEY;
        }

        public final String getNOTIFICATION_MSG_KEY() {
            return AppUtils.NOTIFICATION_MSG_KEY;
        }

        public final String getSLEEPING_TIME_KEY() {
            return AppUtils.SLEEPING_TIME_KEY;
        }

        public final String getWAKEUP_TIME() {
            return AppUtils.WAKEUP_TIME;
        }

        public final String getNOTIFICATION_TONE_URI_KEY() {
            return AppUtils.NOTIFICATION_TONE_URI_KEY;
        }

        public final String getFIRST_RUN_KEY() {
            return AppUtils.FIRST_RUN_KEY;
        }
    }
}
