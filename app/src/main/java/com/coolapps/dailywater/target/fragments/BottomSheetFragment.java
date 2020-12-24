package com.coolapps.dailywater.target.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.coolapps.dailywater.target.MainActivity;
import com.coolapps.dailywater.target.R;
import com.coolapps.dailywater.target.helpers.AlarmHelper;
import com.coolapps.dailywater.target.helpers.SqliteHelper;
import com.coolapps.dailywater.target.utils.AppUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;


public final class BottomSheetFragment extends BottomSheetDialogFragment {
    private HashMap findViewCache;

    public String currentToneUri = "";

    public String customTarget = "";
    private final Context mCtx;


    public int notificFrequency;

    public String notificMsg = "";

    public SharedPreferences sharedPref;

    public long sleepingTime;

    public long wakeupTime;

    public String weight = "";

    public String workTime = "";

    public void clearFindViewByIdCache() {
        HashMap hashMap = this.findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View findCachedViewById(int i) {
        if (this.findViewCache == null) {
            this.findViewCache = new HashMap();
        }
        View view = (View) this.findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View view2 = getView();
        if (view2 == null) {
            return null;
        }
        View findViewById = view2.findViewById(i);
        this.findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    public void onDestroyView() {
        super.onDestroyView();
        clearFindViewByIdCache();
    }

    public BottomSheetFragment(Context mCtx) {
        this.mCtx = mCtx;
        String str = "";
        this.weight = str;
        this.workTime = str;
        this.customTarget = str;
        this.notificMsg = str;
        this.currentToneUri = str;
    }

    public static final SharedPreferences access$getSharedPref$p(BottomSheetFragment $this) {
        SharedPreferences sharedPreferences = $this.sharedPref;
        if (sharedPreferences == null) {

        }
        return sharedPreferences;
    }

    public final Context getMCtx() {
        return this.mCtx;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = this.mCtx.getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
        this.sharedPref = sharedPreferences;

        TextInputLayout textInputLayout = view.findViewById(R.id.etWeight);
        EditText editText = textInputLayout.getEditText();
        if (editText == null) {
        }
        StringBuilder sb = new StringBuilder();
        sb.append("");
        SharedPreferences sharedPreferences2 = this.sharedPref;
        if (sharedPreferences2 == null) {
        }
        sb.append(sharedPreferences2.getInt(AppUtils.Companion.getWEIGHT_KEY(), 0));
        editText.setText(sb.toString());
        TextInputLayout textInputLayout2 = view.findViewById(R.id.etWorkTime);
        EditText editText2 = textInputLayout2.getEditText();
        if (editText2 == null) {
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        SharedPreferences sharedPreferences3 = this.sharedPref;
        if (sharedPreferences3 == null) {
        }
        sb2.append(sharedPreferences3.getInt(AppUtils.Companion.getWORK_TIME_KEY(), 0));
        editText2.setText(sb2.toString());
        TextInputLayout textInputLayout3 = view.findViewById(R.id.etTarget);
        EditText editText3 = textInputLayout3.getEditText();
        if (editText3 == null) {
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("");
        SharedPreferences sharedPreferences4 = this.sharedPref;
        if (sharedPreferences4 == null) {
        }
        sb3.append(sharedPreferences4.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0));
        editText3.setText(sb3.toString());
        TextInputLayout textInputLayout4 = view.findViewById(R.id.etNotificationText);
        EditText editText4 = textInputLayout4.getEditText();
        if (editText4 == null) {
        }
        SharedPreferences sharedPreferences5 = this.sharedPref;
        if (sharedPreferences5 == null) {
        }
        editText4.setText(sharedPreferences5.getString(AppUtils.Companion.getNOTIFICATION_MSG_KEY(), "Hey... Lets drink some water...."));
        SharedPreferences sharedPreferences6 = this.sharedPref;
        if (sharedPreferences6 == null) {
        }
        this.currentToneUri = sharedPreferences6.getString(AppUtils.Companion.getNOTIFICATION_TONE_URI_KEY(), RingtoneManager.getDefaultUri(2).toString());
        TextInputLayout textInputLayout5 = view.findViewById(R.id.etRingtone);
        EditText editText5 = textInputLayout5.getEditText();
        if (editText5 == null) {
        }
        editText5.setText(RingtoneManager.getRingtone(this.mCtx, Uri.parse(this.currentToneUri)).getTitle(this.mCtx));
        ((RadioRealButtonGroup) view.findViewById(R.id.radioNotificItervel)).setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {

                int i = 30;
                if (position != 0) {
                    if (position == 1) {
                        i = 45;
                    } else if (position == 2) {
                        i = 60;
                    }
                }
                BottomSheetFragment.this.notificFrequency = i;
            }
        });
        SharedPreferences sharedPreferences7 = this.sharedPref;
        if (sharedPreferences7 == null) {
        }
        this.notificFrequency = sharedPreferences7.getInt(AppUtils.Companion.getNOTIFICATION_FREQUENCY_KEY(), 30);
        int i = this.notificFrequency;
        if (i == 30) {
            RadioRealButtonGroup radioRealButtonGroup = view.findViewById(R.id.radioNotificItervel);
            radioRealButtonGroup.setPosition(0);
        } else if (i == 45) {
            RadioRealButtonGroup radioRealButtonGroup2 = view.findViewById(R.id.radioNotificItervel);
            radioRealButtonGroup2.setPosition(1);
        } else if (i != 60) {
            RadioRealButtonGroup radioRealButtonGroup3 = view.findViewById(R.id.radioNotificItervel);
            radioRealButtonGroup3.setPosition(0);
            this.notificFrequency = 30;
        } else {
            RadioRealButtonGroup radioRealButtonGroup4 = view.findViewById(R.id.radioNotificItervel);
            radioRealButtonGroup4.setPosition(2);
        }
        TextInputLayout textInputLayout6 = view.findViewById(R.id.etRingtone);
        EditText editText6 = textInputLayout6.getEditText();
        if (editText6 == null) {
        }
        editText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                intent.putExtra("android.intent.extra.ringtone.TYPE", 2);
                intent.putExtra("android.intent.extra.ringtone.TITLE", "Select ringtone for notifications:");
                intent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", false);
                intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
                intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", currentToneUri);
                startActivityForResult(intent, 999);
            }
        });
        SharedPreferences sharedPreferences8 = this.sharedPref;
        if (sharedPreferences8 == null) {
        }
        this.wakeupTime = sharedPreferences8.getLong(AppUtils.Companion.getWAKEUP_TIME(), 1558323000000L);
        SharedPreferences sharedPreferences9 = this.sharedPref;
        if (sharedPreferences9 == null) {
        }
        this.sleepingTime = sharedPreferences9.getLong(AppUtils.Companion.getSLEEPING_TIME_KEY(), 1558369800000L);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.wakeupTime);
        TextInputLayout textInputLayout7 = view.findViewById(R.id.etWakeUpTime);
        EditText editText7 = textInputLayout7.getEditText();
        if (editText7 == null) {
        }
        Object[] objArr = {Integer.valueOf(cal.get(11)), Integer.valueOf(cal.get(12))};
        String format = String.format("%02d:%02d", Arrays.copyOf(objArr, objArr.length));
        editText7.setText(format);
        cal.setTimeInMillis(this.sleepingTime);
        TextInputLayout textInputLayout8 = view.findViewById(R.id.etSleepTime);
        EditText editText8 = textInputLayout8.getEditText();
        if (editText8 == null) {
        }
        Object[] objArr2 = {cal.get(11), cal.get(12)};
        String format2 = String.format("%02d:%02d", Arrays.copyOf(objArr2, objArr2.length));
        editText8.setText(format2);
        TextInputLayout textInputLayout9 = view.findViewById(R.id.etWakeUpTime);
        EditText editText9 = textInputLayout9.getEditText();
        if (editText9 == null) {
        }
        editText9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(wakeupTime);
                TimePickerDialog mTimePicker = new TimePickerDialog(getMCtx(), new TimePickerDialog.OnTimeSetListener() {


                    public final void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar time = Calendar.getInstance();
                        time.set(11, selectedHour);
                        time.set(12, selectedMinute);
                        wakeupTime = time.getTimeInMillis();
                        TextInputLayout textInputLayout = view.findViewById(R.id.etWakeUpTime);
                        EditText editText = textInputLayout.getEditText();
                        if (editText == null) {
                        }
                        Object[] objArr = {selectedHour, selectedMinute};
                        String format = String.format("%02d:%02d", Arrays.copyOf(objArr, objArr.length));
                        editText.setText(format);
                    }
                }, calendar.get(11), calendar.get(12), false);
                mTimePicker.setTitle("Select Wakeup Time");
                mTimePicker.show();
            }
        });
        TextInputLayout textInputLayout10 = view.findViewById(R.id.etSleepTime);
        EditText editText10 = textInputLayout10.getEditText();
        if (editText10 == null) {
        }
        editText10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(sleepingTime);
                TimePickerDialog mTimePicker = new TimePickerDialog(getMCtx(), new TimePickerDialog.OnTimeSetListener() {

                    public final void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar time = Calendar.getInstance();
                        time.set(11, selectedHour);
                        time.set(12, selectedMinute);
                       sleepingTime = time.getTimeInMillis();
                        TextInputLayout textInputLayout = view.findViewById(R.id.etSleepTime);
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
        view.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTarget = BottomSheetFragment.access$getSharedPref$p(BottomSheetFragment.this).getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0);
                BottomSheetFragment bottomSheetFragment = BottomSheetFragment.this;
                TextInputLayout textInputLayout = view.findViewById(R.id.etWeight);
                EditText editText = textInputLayout.getEditText();
                if (editText == null) {
                }
                weight = editText.getText().toString();
                TextInputLayout textInputLayout2 = view.findViewById(R.id.etWorkTime);
                EditText editText2 = textInputLayout2.getEditText();
                if (editText2 == null) {
                }
                workTime = editText2.getText().toString();
                TextInputLayout textInputLayout3 = view.findViewById(R.id.etNotificationText);
                EditText editText3 = textInputLayout3.getEditText();
                if (editText3 == null) {
                }
                notificMsg = editText3.getText().toString();
                TextInputLayout textInputLayout4 = view.findViewById(R.id.etTarget);
                EditText editText4 = textInputLayout4.getEditText();
                if (editText4 == null) {
                }
                customTarget = editText4.getText().toString();
                if (TextUtils.isEmpty(notificMsg)) {
                    Toast.makeText(getMCtx(), "Please a notification message", Toast.LENGTH_SHORT).show();
                } else if (notificFrequency == 0) {
                    Toast.makeText(getMCtx(), "Please select a notification frequency", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(weight)) {
                    Toast.makeText(getMCtx(), "Please input your weight", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(weight) > 200 || Integer.parseInt(weight) < 20) {
                    Toast.makeText(getMCtx(), "Please input a valid weight", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(workTime)) {
                    Toast.makeText(getMCtx(), "Please input your workout time", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(workTime) > 500 || Integer.parseInt(workTime) < 0) {
                    Toast.makeText(getMCtx(), "Please input a valid workout time", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(customTarget)) {
                    Toast.makeText(getMCtx(), "Please input your custom target", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = BottomSheetFragment.access$getSharedPref$p(bottomSheetFragment).edit();
                    editor.putInt(AppUtils.Companion.getWEIGHT_KEY(), Integer.parseInt(weight));
                    editor.putInt(AppUtils.Companion.getWORK_TIME_KEY(), Integer.parseInt(workTime));
                    editor.putLong(AppUtils.Companion.getWAKEUP_TIME(), wakeupTime);
                    editor.putLong(AppUtils.Companion.getSLEEPING_TIME_KEY(), sleepingTime);
                    editor.putString(AppUtils.Companion.getNOTIFICATION_MSG_KEY(), notificMsg);
                    editor.putInt(AppUtils.Companion.getNOTIFICATION_FREQUENCY_KEY(), notificFrequency);
                    SqliteHelper sqliteHelper = new SqliteHelper(getMCtx());
                    if (currentTarget != Integer.parseInt(customTarget)) {
                        editor.putInt(AppUtils.Companion.getTOTAL_INTAKE(), Integer.parseInt(customTarget));
                        String currentDate = AppUtils.Companion.getCurrentDate();
                        if (currentDate == null) {
                        }
                        sqliteHelper.updateTotalIntake(currentDate, Integer.parseInt(customTarget));
                    } else {
                        double totalIntake = AppUtils.Companion.calculateIntake(Integer.parseInt(weight), Integer.parseInt(workTime));
                        DecimalFormat df = new DecimalFormat("#");
                        df.setRoundingMode(RoundingMode.CEILING);
                        String total_intake = AppUtils.Companion.getTOTAL_INTAKE();
                        String format = df.format(totalIntake);
                        editor.putInt(total_intake, Integer.parseInt(format));
                        String currentDate2 = AppUtils.Companion.getCurrentDate();
                        if (currentDate2 == null) {
                        }
                        String format2 = df.format(totalIntake);
                        sqliteHelper.updateTotalIntake(currentDate2, Integer.parseInt(format2));
                    }
                    editor.apply();
                    Toast.makeText(getMCtx(), "Values updated successfully", Toast.LENGTH_SHORT).show();
                    AlarmHelper alarmHelper = new AlarmHelper();
                    alarmHelper.cancelAlarm(getMCtx());
                    alarmHelper.setAlarm(getMCtx(), BottomSheetFragment.access$getSharedPref$p(BottomSheetFragment.this).getInt(AppUtils.Companion.getNOTIFICATION_FREQUENCY_KEY(), 30));
                    dismiss();

//                   mCtx.updateValues();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && requestCode == 999) {
            if (data == null) {
            }
            Parcelable parcelableExtra = data.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            if (parcelableExtra != null) {
                Uri uri = (Uri) parcelableExtra;
                this.currentToneUri = uri.toString();
                SharedPreferences sharedPreferences = this.sharedPref;
                if (sharedPreferences == null) {
                }
                sharedPreferences.edit().putString(AppUtils.Companion.getNOTIFICATION_TONE_URI_KEY(), this.currentToneUri).apply();
                Ringtone ringtone = RingtoneManager.getRingtone(this.mCtx, uri);
                TextInputLayout textInputLayout = (TextInputLayout) findCachedViewById(R.id.etRingtone);
                EditText editText = textInputLayout.getEditText();
                if (editText == null) {
                }
                editText.setText(ringtone.getTitle(this.mCtx));
                return;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
