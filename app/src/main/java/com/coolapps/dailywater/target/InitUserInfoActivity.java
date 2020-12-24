package com.coolapps.dailywater.target;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.coolapps.dailywater.target.utils.AdsUtility;
import com.coolapps.dailywater.target.utils.AppUtils;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public final class InitUserInfoActivity extends AppCompatActivity {
    private HashMap findViewCache;

    public boolean doubleBackToExitPressedOnce;

    public SharedPreferences sharedPref;

    public long sleepingTime;

    public long wakeupTime;

    public String weight = "";

    public String workTime = "";

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this.findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this.findViewCache == null) {
            this.findViewCache = new HashMap();
        }
        View view = (View) this.findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this.findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    public static final  SharedPreferences accessgetSharedPrefp(InitUserInfoActivity $this) {
        SharedPreferences sharedPreferences = $this.sharedPref;
        if (sharedPreferences == null) {
        }
        return sharedPreferences;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(8192);
        }
        setContentView((int) R.layout.activity_init_user_info);
        AdsUtility.InterstitialAdmob(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
        this.sharedPref = sharedPreferences;
        SharedPreferences sharedPreferences2 = this.sharedPref;
        if (sharedPreferences2 == null) {
        }
        this.wakeupTime = sharedPreferences2.getLong(AppUtils.Companion.getWAKEUP_TIME(), 1558323000000L);
        SharedPreferences sharedPreferences3 = this.sharedPref;
        if (sharedPreferences3 == null) {
        }
        this.sleepingTime = sharedPreferences3.getLong(AppUtils.Companion.getSLEEPING_TIME_KEY(), 1558369800000L);
        TextInputLayout textInputLayout = (TextInputLayout) _$_findCachedViewById(R.id.etWakeUpTime);
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
        }
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(wakeupTime);
                TimePickerDialog mTimePicker = new TimePickerDialog(InitUserInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {


                    public final void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar time = Calendar.getInstance();
                        time.set(11, selectedHour);
                        time.set(12, selectedMinute);
                        wakeupTime = time.getTimeInMillis();
                        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.etWakeUpTime);
                        EditText editText = textInputLayout.getEditText();
                        if (editText == null) {

                        }
                        Object[] objArr = {Integer.valueOf(selectedHour), Integer.valueOf(selectedMinute)};
                        String format = String.format("%02d:%02d", Arrays.copyOf(objArr, objArr.length));
                        editText.setText(format);
                    }
                }, calendar.get(11), calendar.get(12), false);
                mTimePicker.setTitle("Select Wakeup Time");
                mTimePicker.show();
            }
        });
        TextInputLayout textInputLayout2 = (TextInputLayout) _$_findCachedViewById(R.id.etSleepTime);
        EditText editText2 = textInputLayout2.getEditText();
        if (editText2 == null) {
        }
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(sleepingTime);
                TimePickerDialog mTimePicker = new TimePickerDialog(InitUserInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {


                    public final void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar time = Calendar.getInstance();
                        time.set(11, selectedHour);
                        time.set(12, selectedMinute);

                        sleepingTime = time.getTimeInMillis();
                        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.etSleepTime);
                        EditText editText = textInputLayout.getEditText();

                        Object[] objArr = {Integer.valueOf(selectedHour), Integer.valueOf(selectedMinute)};
                        String format = String.format("%02d:%02d", Arrays.copyOf(objArr, objArr.length));
                        editText.setText(format);
                    }
                }, calendar.get(11), calendar.get(12), false);
                mTimePicker.setTitle("Select Sleeping Time");
                mTimePicker.show();
            }
        });
        ((Button) findViewById(R.id.btnContinue)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object systemService = getSystemService(INPUT_METHOD_SERVICE);
                if (systemService != null) {
                    ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.init_user_info_parent_layout);
                    ((InputMethodManager) systemService).hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
                    TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.etWeight);
                    EditText editText = textInputLayout.getEditText();
                    if (editText == null) {
                    }
                    weight = editText.getText().toString();
                    TextInputLayout textInputLayout2 = (TextInputLayout) findViewById(R.id.etWorkTime);
                    EditText editText2 = textInputLayout2.getEditText();
                    if (editText2 == null) {
                    }
                    workTime = editText2.getText().toString();
                    if (TextUtils.isEmpty(weight)) {
                        Snackbar.make(v, (CharSequence) "Please input your weight", BaseTransientBottomBar.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(weight) > 200 || Integer.parseInt(weight) < 20) {
                        Snackbar.make(v, (CharSequence) "Please input a valid weight", BaseTransientBottomBar.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(workTime)) {
                        Snackbar.make(v, (CharSequence) "Please input your workout time", BaseTransientBottomBar.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(workTime) > 500 || Integer.parseInt(workTime) < 0) {
                        Snackbar.make(v, (CharSequence) "Please input a valid workout time", BaseTransientBottomBar.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor editor = InitUserInfoActivity.accessgetSharedPrefp(InitUserInfoActivity.this).edit();
                        editor.putInt(AppUtils.Companion.getWEIGHT_KEY(), Integer.parseInt(weight));
                        editor.putInt(AppUtils.Companion.getWORK_TIME_KEY(), Integer.parseInt(workTime));
                        editor.putLong(AppUtils.Companion.getWAKEUP_TIME(), wakeupTime);
                        editor.putLong(AppUtils.Companion.getSLEEPING_TIME_KEY(), sleepingTime);
                        editor.putBoolean(AppUtils.Companion.getFIRST_RUN_KEY(), false);
                        double totalIntake = AppUtils.Companion.calculateIntake(Integer.parseInt(weight), Integer.parseInt(workTime));
                        DecimalFormat df = new DecimalFormat("#");
                        df.setRoundingMode(RoundingMode.CEILING);
                        String total_intake = AppUtils.Companion.getTOTAL_INTAKE();
                        String format = df.format(totalIntake);
                        editor.putInt(total_intake, Integer.parseInt(format));
                        editor.apply();
                        startActivity(new Intent(InitUserInfoActivity.this, MainActivity.class));
                        finish();
                    }
                    AdsUtility.showIntestitialAds();
                    return;
                }

            }
        });
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Window window = getWindow();
        Snackbar.make(window.getDecorView(), (CharSequence) "Please click BACK again to exit", BaseTransientBottomBar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }
}
