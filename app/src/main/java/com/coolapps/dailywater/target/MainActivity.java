package com.coolapps.dailywater.target;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.coolapps.dailywater.target.fragments.BottomSheetFragment;
import com.coolapps.dailywater.target.helpers.AlarmHelper;
import com.coolapps.dailywater.target.helpers.SqliteHelper;
import com.coolapps.dailywater.target.utils.AdsUtility;
import com.coolapps.dailywater.target.utils.AppUtils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import params.com.stepprogressview.StepProgressView;

public final class MainActivity extends AppCompatActivity {
    private LinearLayout banner;

    public String dateNow;

    public boolean doubleBackToExitPressedOnce;

    public int inTook;

    public boolean notificStatus;

    public Integer selectedOption;

    public SharedPreferences sharedPref;

    public Snackbar snackbar;

    public SqliteHelper sqliteHelper;

    public int totalIntake;

    public static final  String access$getDateNow$p(MainActivity $this) {
        String str = $this.dateNow;
        if (str == null) {
        }
        return str;
    }

    public static final  SharedPreferences access$getSharedPref$p(MainActivity $this) {
        SharedPreferences sharedPreferences = $this.sharedPref;
        if (sharedPreferences == null) {
        }
        return sharedPreferences;
    }

    public static final  SqliteHelper access$getSqliteHelper$p(MainActivity $this) {
        SqliteHelper sqliteHelper2 = $this.sqliteHelper;
        if (sqliteHelper2 == null) {
        }
        return sqliteHelper2;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, AdsUtility.admobAppId);
        SharedPreferences sharedPreferences = getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
        this.sharedPref = sharedPreferences;
        this.sqliteHelper = new SqliteHelper(this);
        AdsUtility.InterstitialAdmob(this);
        SharedPreferences sharedPreferences2 = this.sharedPref;
        if (sharedPreferences2 == null) {
        }
        this.totalIntake = sharedPreferences2.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0);
        SharedPreferences sharedPreferences3 = this.sharedPref;
        if (sharedPreferences3 == null) {
        }
        if (sharedPreferences3.getBoolean(AppUtils.Companion.getFIRST_RUN_KEY(), true)) {
            startActivity(new Intent(this, WalkThroughActivity.class));
            finish();
        } else if (this.totalIntake <= 0) {
            startActivity(new Intent(this, InitUserInfoActivity.class));
            finish();
        }
        String currentDate = AppUtils.Companion.getCurrentDate();
        if (currentDate == null) {
        }
        this.dateNow = currentDate;
        View findViewById = findViewById(R.id.banner);
        this.banner = (LinearLayout) findViewById;
        Activity activity = this;
        LinearLayout linearLayout = this.banner;
        if (linearLayout == null) {
        }
        AdsUtility.admobBannerCall(activity, linearLayout);
    }

    public  void updateValues() {
        totalIntake = sharedPref.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0);
        inTook = sqliteHelper.getIntook(dateNow);
        setWaterLevel(inTook, totalIntake);
    }


    public void onStart() {
        super.onStart();
        TypedValue outValue = new TypedValue();
        Context applicationContext = getApplicationContext();
        applicationContext.getTheme().resolveAttribute(16843534, outValue, true);
        SharedPreferences sharedPreferences = this.sharedPref;
        if (sharedPreferences == null) {
        }
        this.notificStatus = sharedPreferences.getBoolean(AppUtils.Companion.getNOTIFICATION_STATUS_KEY(), true);
        AlarmHelper alarm = new AlarmHelper();
        if (!alarm.checkAlarm(this) && this.notificStatus) {
            ((FloatingActionButton) findViewById(R.id.btnNotific)).setImageDrawable(getDrawable(R.drawable.ic_bell));
            Context context = this;
            SharedPreferences sharedPreferences2 = this.sharedPref;
            if (sharedPreferences2 == null) {
            }
            alarm.setAlarm(context, sharedPreferences2.getInt(AppUtils.Companion.getNOTIFICATION_FREQUENCY_KEY(), 30));
        }
        if (this.notificStatus) {
            ((FloatingActionButton) findViewById(R.id.btnNotific)).setImageDrawable(getDrawable(R.drawable.ic_bell));
        } else {
            ((FloatingActionButton) findViewById(R.id.btnNotific)).setImageDrawable(getDrawable(R.drawable.ic_bell_disabled));
        }
        SqliteHelper sqliteHelper2 = this.sqliteHelper;
        if (sqliteHelper2 == null) {
        }
        String str = this.dateNow;
        if (str == null) {
        }
        sqliteHelper2.addAll(str, 0, this.totalIntake);
        updateValues();
        findViewById(R.id.btnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment(getApplication());
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                AdsUtility.showIntestitialAds();
            }
        });
        findViewById(R.id.fabAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOption != null) {
                    if ((inTook * 100) / totalIntake <= 140) {
                        SqliteHelper access$getSqliteHelper$p = MainActivity.access$getSqliteHelper$p(MainActivity.this);
                        String access$getDateNow$p = MainActivity.access$getDateNow$p(MainActivity.this);
                        Integer access$getSelectedOption$p = selectedOption;
                        if (access$getSelectedOption$p == null) {

                        }
                        if (access$getSqliteHelper$p.addIntook(access$getDateNow$p, access$getSelectedOption$p.intValue()) > 0) {
                            int access$getInTook$p = inTook;
                            Integer access$getSelectedOption$p2 = selectedOption;
                            if (access$getSelectedOption$p2 == null) {
                            }
                            inTook = access$getInTook$p + access$getSelectedOption$p2.intValue();
                            setWaterLevel(inTook, totalIntake);
                            Snackbar.make(v, "Your water intake was saved...!!", BaseTransientBottomBar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(v, "You already achieved the goal", BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                    selectedOption = null;
                    TextView textView = findViewById(R.id.tvCustom);
                    textView.setText("Custom");
                    LinearLayout linearLayout = findViewById(R.id.op50ml);
                    linearLayout.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout2 = findViewById(R.id.op100ml);
                    linearLayout2.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout3 = findViewById(R.id.op150ml);
                    linearLayout3.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout4 = findViewById(R.id.op200ml);
                    linearLayout4.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout5 = findViewById(R.id.op250ml);
                    linearLayout5.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout6 = findViewById(R.id.opCustom);
                    linearLayout6.setBackground(getDrawable(outValue.resourceId));
                    return;
                }
                YoYo.with(Techniques.Shake).duration(700).playOn(findViewById(R.id.cardView));
                Snackbar.make(v, "Please select an option", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btnNotific).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = MainActivity.this;
                mainActivity.notificStatus = !mainActivity.notificStatus;
                MainActivity.access$getSharedPref$p(mainActivity).edit().putBoolean(AppUtils.Companion.getNOTIFICATION_STATUS_KEY(), mainActivity.notificStatus).apply();
                if (mainActivity.notificStatus) {
                    ((FloatingActionButton) mainActivity.findViewById(R.id.btnNotific)).setImageDrawable(mainActivity.getDrawable(R.drawable.ic_bell));
                    Snackbar.make(v, "Notification Enabled..", BaseTransientBottomBar.LENGTH_SHORT).show();
                    AlarmHelper alarmHelper = alarm;
                    alarmHelper.setAlarm(mainActivity, MainActivity.access$getSharedPref$p(mainActivity).getInt(AppUtils.Companion.getNOTIFICATION_FREQUENCY_KEY(), 30));
                    return;
                }
                ((FloatingActionButton) mainActivity.findViewById(R.id.btnNotific)).setImageDrawable(mainActivity.getDrawable(R.drawable.ic_bell_disabled));
                Snackbar.make(v, "Notification Disabled..", BaseTransientBottomBar.LENGTH_SHORT).show();
                alarm.cancelAlarm(mainActivity);
            }
        });
        findViewById(R.id.btnStats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatusActivity.class));
                AdsUtility.showIntestitialAds();
            }
        });
        findViewById(R.id.op50ml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar access$getSnackbar$p;
                if (!(snackbar == null || (access$getSnackbar$p = snackbar) == null)) {
                    access$getSnackbar$p.dismiss();
                }
                selectedOption = 50;
                LinearLayout linearLayout = findViewById(R.id.op50ml);
                linearLayout.setBackground(getDrawable(R.drawable.option_select_bg));
                LinearLayout linearLayout2 = findViewById(R.id.op100ml);
                linearLayout2.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout3 = findViewById(R.id.op150ml);
                linearLayout3.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout4 = findViewById(R.id.op200ml);
                linearLayout4.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout5 = findViewById(R.id.op250ml);
                linearLayout5.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout6 = findViewById(R.id.opCustom);
                linearLayout6.setBackground(getDrawable(outValue.resourceId));
            }
        });
        findViewById(R.id.op100ml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar access$getSnackbar$p;
                if (!(snackbar == null || (access$getSnackbar$p = snackbar) == null)) {
                    access$getSnackbar$p.dismiss();
                }
                selectedOption = 100;
                LinearLayout linearLayout = findViewById(R.id.op50ml);
                linearLayout.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout2 = findViewById(R.id.op100ml);
                linearLayout2.setBackground(getDrawable(R.drawable.option_select_bg));
                LinearLayout linearLayout3 = findViewById(R.id.op150ml);
                linearLayout3.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout4 = findViewById(R.id.op200ml);
                linearLayout4.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout5 = findViewById(R.id.op250ml);
                linearLayout5.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout6 = findViewById(R.id.opCustom);
                linearLayout6.setBackground(getDrawable(outValue.resourceId));
            }
        });
        findViewById(R.id.op150ml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar access$getSnackbar$p;
                if (!(snackbar == null || (access$getSnackbar$p = snackbar) == null)) {
                    access$getSnackbar$p.dismiss();
                }
                selectedOption = 150;
                LinearLayout linearLayout = findViewById(R.id.op50ml);
                linearLayout.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout2 = findViewById(R.id.op100ml);
                linearLayout2.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout3 = findViewById(R.id.op150ml);
                linearLayout3.setBackground(getDrawable(R.drawable.option_select_bg));
                LinearLayout linearLayout4 = findViewById(R.id.op200ml);
                linearLayout4.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout5 = findViewById(R.id.op250ml);
                linearLayout5.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout6 = findViewById(R.id.opCustom);
                linearLayout6.setBackground(getDrawable(outValue.resourceId));
            }
        });
        findViewById(R.id.op200ml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar access$getSnackbar$p;
                if (!(snackbar == null || (access$getSnackbar$p = snackbar) == null)) {
                    access$getSnackbar$p.dismiss();
                }
                selectedOption = Integer.valueOf(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                LinearLayout linearLayout = findViewById(R.id.op50ml);
                linearLayout.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout2 = findViewById(R.id.op100ml);
                linearLayout2.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout3 = findViewById(R.id.op150ml);
                linearLayout3.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout4 = findViewById(R.id.op200ml);
                linearLayout4.setBackground(getDrawable(R.drawable.option_select_bg));
                LinearLayout linearLayout5 = findViewById(R.id.op250ml);
                linearLayout5.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout6 = findViewById(R.id.opCustom);
                linearLayout6.setBackground(getDrawable(outValue.resourceId));
            }
        });
        findViewById(R.id.op250ml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar access$getSnackbar$p;
                if (!(snackbar == null || (access$getSnackbar$p = snackbar) == null)) {
                    access$getSnackbar$p.dismiss();
                }
                selectedOption = Integer.valueOf(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
                LinearLayout linearLayout = findViewById(R.id.op50ml);
                linearLayout.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout2 = findViewById(R.id.op100ml);
                linearLayout2.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout3 = findViewById(R.id.op150ml);
                linearLayout3.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout4 = findViewById(R.id.op200ml);
                linearLayout4.setBackground(getDrawable(outValue.resourceId));
                LinearLayout linearLayout5 = findViewById(R.id.op250ml);
                linearLayout5.setBackground(getDrawable(R.drawable.option_select_bg));
                LinearLayout linearLayout6 = findViewById(R.id.opCustom);
                linearLayout6.setBackground(getDrawable(outValue.resourceId));
            }
        });
        findViewById(R.id.opCustom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar access$getSnackbar$p;
                if (!(snackbar == null || (access$getSnackbar$p = snackbar) == null)) {
                    access$getSnackbar$p.dismiss();
                }
                View promptsView = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(promptsView);
                View findViewById = promptsView.findViewById(R.id.etCustomInput);
                if (findViewById != null) {
                    final TextInputLayout userInput = (TextInputLayout) findViewById;
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public final void onClick(DialogInterface dialog, int id) {
                            EditText editText = userInput.getEditText();
                            if (editText == null) {
                            }
                            String inputText = editText.getText().toString();
                            if (!TextUtils.isEmpty(inputText)) {
                                TextView textView = findViewById(R.id.tvCustom);
                                textView.setText(inputText + " ml");
                                selectedOption = Integer.valueOf(Integer.parseInt(inputText));
                            }
                        }
                    }).setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialogBuilder.create().show();
                    LinearLayout linearLayout = findViewById(R.id.op50ml);
                    linearLayout.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout2 = findViewById(R.id.op100ml);
                    linearLayout2.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout3 = findViewById(R.id.op150ml);
                    linearLayout3.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout4 = findViewById(R.id.op200ml);
                    linearLayout4.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout5 = findViewById(R.id.op250ml);
                    linearLayout5.setBackground(getDrawable(outValue.resourceId));
                    LinearLayout linearLayout6 = findViewById(R.id.opCustom);
                    linearLayout6.setBackground(getDrawable(R.drawable.option_select_bg));
                    return;
                }
            }
        });
    }


    public final void setWaterLevel(int inTook, int totalIntake) {
        YoYo.with(Techniques.SlideInDown).duration(500).playOn(findViewById(R.id.tvIntook));
        TextView tvInTake = findViewById(R.id.tvIntook);
        tvInTake.setText(String.valueOf(inTook));
        TextView tvTotalInTake = findViewById(R.id.tvTotalIntake);
        tvTotalInTake.setText('/' + totalIntake + " ml");
        YoYo.with(Techniques.Pulse).duration(500).playOn(findViewById(R.id.intakeProgress));
        ((StepProgressView) findViewById(R.id.intakeProgress)).setCurrentProgress((int) ((((float) inTook) / ((float) totalIntake)) * ((float) 100)));
        if ((inTook * 100) / totalIntake > 140) {
            Snackbar.make(findViewById(R.id.main_activity_parent), "You achieved the goal", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Window window = getWindow();
        Snackbar.make(window.getDecorView().findViewById(R.id.main_activity_parent), "Please click BACK again to exit", BaseTransientBottomBar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);
    }
}
