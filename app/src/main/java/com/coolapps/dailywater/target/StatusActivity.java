package com.coolapps.dailywater.target;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.coolapps.dailywater.target.helpers.SqliteHelper;
import com.coolapps.dailywater.target.utils.AdsUtility;
import com.coolapps.dailywater.target.utils.AppUtils;
import com.coolapps.dailywater.target.utils.ChartXValueFormatter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

import me.itangqi.waveloadingview.WaveLoadingView;

public final class StatusActivity extends AppCompatActivity {
    private LinearLayout banner;
    private SharedPreferences sharedPref;
    private SqliteHelper sqliteHelper;
    public float totalGlasses;
    public float totalPercentage;
    private static final String TAG = "StatusActivity";
    private ArrayList<Entry> entries = new ArrayList<>();
    private final ArrayList<String> dateArray = new ArrayList<>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        AdsUtility.InterstitialAdmob(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
        this.sharedPref = sharedPreferences;
        this.sqliteHelper = new SqliteHelper(this);

        banner = findViewById(R.id.banner);
        TextView textView = findViewById(R.id.remainingIntake);
        TextView textView3 = findViewById(R.id.targetIntake);
        WaveLoadingView waveLoadingView = findViewById(R.id.waterLevelView);

        LinearLayout linearLayout = banner;
        AdsUtility.admobBannerCall(this, linearLayout);


        Cursor cursor = sqliteHelper.getAllStats();
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();

            for (int i = 0; i < count; i++) {
                dateArray.add(cursor.getString(1));
                float percent = (((float) cursor.getInt(2)) / ((float) cursor.getInt(3))) * ((float) 100);
                this.totalPercentage += percent;
                this.totalGlasses += (float) cursor.getInt(2);
                entries.add(new Entry((float) i, percent));
                Log.d(TAG, "onCreate: "+i);
                cursor.moveToNext();
            }
        } else {

            Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();
        }
        if (!entries.isEmpty()) {
//            Log.d(TAG, "onCreate: Enteries Size"+entries.size());
//            Log.d(TAG, "onCreate: Date Array size"+dateArray.size());
            LineChart lineChart = findViewById(R.id.chart);
            lineChart.getDescription().setEnabled(false);

            lineChart.animateY(1000, Easing.Linear);
            lineChart.getViewPortHandler().setMaximumScaleX(1.5f);
            lineChart.getXAxis().setDrawGridLines(false);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.TOP);
            xAxis.setGranularityEnabled(true);

            Legend legend = lineChart.getLegend();
            legend.setEnabled(false);

            lineChart.fitScreen();
            lineChart.setAutoScaleMinMaxEnabled(true);
            lineChart.setScaleX(1.0f);
            lineChart.setPinchZoom(true);
            lineChart.setScaleXEnabled(true);
            lineChart.setScaleYEnabled(false);

            YAxis axisLeft = lineChart.getAxisLeft();
            axisLeft.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            XAxis xAxis3 = lineChart.getXAxis();
            xAxis3.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            lineChart.getAxisLeft().setDrawAxisLine(false);
            lineChart.getXAxis().setDrawAxisLine(false);
            lineChart.setDrawMarkers(false);
            XAxis xAxis4 = lineChart.getXAxis();
            xAxis4.setLabelCount(5);
            YAxis rightAxix = lineChart.getAxisRight();
            rightAxix.setDrawGridLines(false);
            rightAxix.setDrawZeroLine(false);
            rightAxix.setDrawAxisLine(false);
            rightAxix.setDrawLabels(false);

            LineDataSet dataSet = new LineDataSet(entries, "Label");
            dataSet.setDrawCircles(false);
            dataSet.setLineWidth(2.5f);
            dataSet.setColor(ContextCompat.getColor(this, R.color.colorSecondaryDark));
            dataSet.setDrawFilled(true);
            dataSet.setFillDrawable(getDrawable(R.drawable.graph_fill_gradiant));
            dataSet.setDrawValues(false);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            LineData lineData = new LineData(dataSet);

            XAxis xAxis5 = lineChart.getXAxis();

            xAxis5.setValueFormatter(new ChartXValueFormatter(dateArray));
            lineChart.setData(lineData);
            findViewById(R.id.chart).invalidate();
            int i2 = sharedPref.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0);
            String currentDate = AppUtils.Companion.getCurrentDate();
            int remaining = i2 - sqliteHelper.getIntook(currentDate);

            if (remaining > 0) {
                textView.setText(remaining + " ml");
            } else {
                textView.setText("0 ml");
            }

            StringBuilder sb = new StringBuilder();
            sb.append(sharedPref.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0));
            sb.append(" ml");
            textView3.setText(sb.toString());
            int intook = sqliteHelper.getIntook(AppUtils.Companion.getCurrentDate()) * 100;
            int percentage = intook / sharedPref.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(percentage);
            sb2.append('%');
            waveLoadingView.setCenterTitle(sb2.toString());
            waveLoadingView.setProgressValue(percentage);
        }
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                AdsUtility.showIntestitialAds();
            }
        });
    }
}
