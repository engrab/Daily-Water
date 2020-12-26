package com.coolapps.dailywater.target;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

import me.itangqi.waveloadingview.WaveLoadingView;

public final class StatusActivity extends AppCompatActivity {
    private HashMap<Integer, View> findViewCache;
    private LinearLayout banner;
    private SharedPreferences sharedPref;
    private SqliteHelper sqliteHelper;
    public float totalGlasses;
    public float totalPercentage;



    public View findCachedViewById(int i) {
        if (this.findViewCache == null) {
            this.findViewCache = new HashMap<Integer, View>();
        }
        View view = this.findViewCache.get(i);
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this.findViewCache.put(i, findViewById);
        return findViewById;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        AdsUtility.InterstitialAdmob(this);
        SharedPreferences sharedPreferences = getSharedPreferences(AppUtils.Companion.getUSERS_SHARED_PREF(), AppUtils.Companion.getPRIVATE_MODE());
        this.sharedPref = sharedPreferences;
        this.sqliteHelper = new SqliteHelper(this);
        findCachedViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                AdsUtility.showIntestitialAds();
            }
        });
        banner = findViewById(R.id.banner);

        LinearLayout linearLayout = banner;
        AdsUtility.admobBannerCall(this, linearLayout);
        ArrayList entries = new ArrayList<>();
        ArrayList<String> dateArray = new ArrayList<>();

        Cursor cursor = sqliteHelper.getAllStats();
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();
            for (int i = 0; i < count; i++) {
                dateArray.add(cursor.getString(1));
                float percent = (((float) cursor.getInt(2)) / ((float) cursor.getInt(3))) * ((float) 100);
                this.totalPercentage += percent;
                this.totalGlasses += (float) cursor.getInt(2);
                entries.add(new Entry((float) i, percent));
                cursor.moveToNext();
            }
        } else {
            Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show();
        }
        if (!entries.isEmpty()) {
            LineChart lineChart = (LineChart) findCachedViewById(R.id.chart);
            Description description = lineChart.getDescription();
            description.setEnabled(false);
            ((LineChart) findCachedViewById(R.id.chart)).animateY(1000, Easing.Linear);
            LineChart lineChart2 = (LineChart) findCachedViewById(R.id.chart);
            lineChart2.getViewPortHandler().setMaximumScaleX(1.5f);
            LineChart lineChart3 = (LineChart) findCachedViewById(R.id.chart);
            lineChart3.getXAxis().setDrawGridLines(false);
            LineChart lineChart4 = (LineChart) findCachedViewById(R.id.chart);
            XAxis xAxis = lineChart4.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.TOP);
            LineChart lineChart5 = (LineChart) findCachedViewById(R.id.chart);
            XAxis xAxis2 = lineChart5.getXAxis();
            xAxis2.setGranularityEnabled(true);
            LineChart lineChart6 = (LineChart) findCachedViewById(R.id.chart);
            Legend legend = lineChart6.getLegend();
            legend.setEnabled(false);
            ((LineChart) findCachedViewById(R.id.chart)).fitScreen();
            LineChart lineChart7 = (LineChart) findCachedViewById(R.id.chart);
            lineChart7.setAutoScaleMinMaxEnabled(true);
            LineChart lineChart8 = (LineChart) findCachedViewById(R.id.chart);
            lineChart8.setScaleX(1.0f);
            ((LineChart) findCachedViewById(R.id.chart)).setPinchZoom(true);
            LineChart lineChart9 = (LineChart) findCachedViewById(R.id.chart);
            lineChart9.setScaleXEnabled(true);
            LineChart lineChart10 = (LineChart) findCachedViewById(R.id.chart);
            lineChart10.setScaleYEnabled(false);
            LineChart lineChart11 = (LineChart) findCachedViewById(R.id.chart);
            YAxis axisLeft = lineChart11.getAxisLeft();
            axisLeft.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            LineChart lineChart12 = (LineChart) findCachedViewById(R.id.chart);
            XAxis xAxis3 = lineChart12.getXAxis();
            xAxis3.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            LineChart lineChart13 = (LineChart) findCachedViewById(R.id.chart);
            lineChart13.getAxisLeft().setDrawAxisLine(false);
            LineChart lineChart14 = (LineChart) findCachedViewById(R.id.chart);
            lineChart14.getXAxis().setDrawAxisLine(false);
            ((LineChart) findCachedViewById(R.id.chart)).setDrawMarkers(false);
            LineChart lineChart15 = (LineChart) findCachedViewById(R.id.chart);
            XAxis xAxis4 = lineChart15.getXAxis();
            xAxis4.setLabelCount(5);
            LineChart lineChart16 = (LineChart) findCachedViewById(R.id.chart);
            YAxis rightAxix = lineChart16.getAxisRight();
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
            LineChart lineChart17 = (LineChart) findCachedViewById(R.id.chart);
            XAxis xAxis5 = lineChart17.getXAxis();
            xAxis5.setValueFormatter(new ChartXValueFormatter(dateArray));
            LineChart lineChart18 = (LineChart) findCachedViewById(R.id.chart);
            lineChart18.setData(lineData);
            findCachedViewById(R.id.chart).invalidate();
            int i2 = sharedPref.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0);
            String currentDate = AppUtils.Companion.getCurrentDate();
            int remaining = i2 - sqliteHelper.getIntook(currentDate);
            if (remaining > 0) {
                TextView textView = (TextView) findCachedViewById(R.id.remainingIntake);
                textView.setText(remaining + " ml");
            } else {
                TextView textView2 = (TextView) findCachedViewById(R.id.remainingIntake);
                textView2.setText("0 ml");
            }
            TextView textView3 = (TextView) findCachedViewById(R.id.targetIntake);
            StringBuilder sb = new StringBuilder();
            sb.append(sharedPref.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0));
            sb.append(" ml");
            textView3.setText(sb.toString());
            String currentDate2 = AppUtils.Companion.getCurrentDate();
            int intook = sqliteHelper.getIntook(currentDate2) * 100;
            SharedPreferences sharedPreferences4 = this.sharedPref;
            int percentage = intook / sharedPreferences4.getInt(AppUtils.Companion.getTOTAL_INTAKE(), 0);
            WaveLoadingView waveLoadingView = (WaveLoadingView) findCachedViewById(R.id.waterLevelView);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(percentage);
            sb2.append('%');
            waveLoadingView.setCenterTitle(sb2.toString());
            WaveLoadingView waveLoadingView2 = (WaveLoadingView) findCachedViewById(R.id.waterLevelView);
            waveLoadingView2.setProgressValue(percentage);
        }
    }
}
