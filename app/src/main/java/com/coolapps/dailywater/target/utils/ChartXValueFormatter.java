package com.coolapps.dailywater.target.utils;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;


public final class ChartXValueFormatter extends ValueFormatter {
    private final ArrayList<String> dateArray;
    private static final String TAG = "ChartXValueFormatter";

    public ChartXValueFormatter(ArrayList<String> dateArray) {
        this.dateArray = dateArray;
    }

    public final ArrayList<String> getDateArray() {
        return this.dateArray;
    }

    public String getAxisLabel(float value, AxisBase axis) {

        return dateArray.get(0);


    }
}
