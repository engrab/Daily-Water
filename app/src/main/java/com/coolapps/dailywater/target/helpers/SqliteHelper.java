package com.coolapps.dailywater.target.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Closeable;


public final class SqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DrinkWaterDB";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_DATE = "KeyDate";
    private static final String KEY_ID = "KeyId";
    private static final String KEY_INTOOK = "KeyInTook";
    private static final String KEY_TOTAL_INTAKE = "KeyTotalInTake";
    private static final String TABLE_NAME = "DrinkWater";
    private final Context context;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public final Context getContext() {
        return context;
    }



    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATE + " TEXT UNIQUE," + KEY_INTOOK + " INT," + KEY_TOTAL_INTAKE + " INT" + ")";
        if (db != null) {
            db.execSQL(CREATE_STATS_TABLE);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public final long addAll(String date, int intook, int totalintake) {
        if (checkExistance(date) != 0) {
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_INTOOK, intook);
        values.put(KEY_TOTAL_INTAKE, totalintake);
        SQLiteDatabase db = getWritableDatabase();
        long response = db.insert(TABLE_NAME, null, values);
        db.close();
        return response;
    }


    public final int getIntook(String date) {
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT ");
        selectQuery.append(KEY_INTOOK);
        selectQuery.append(" FROM ");
        selectQuery.append(TABLE_NAME);
        selectQuery.append(" WHERE ");
        selectQuery.append(KEY_DATE);
        selectQuery.append(" = ?");

        Closeable rawQuery = getReadableDatabase().rawQuery(selectQuery.toString(), new String[]{date});
        Cursor it = (Cursor) rawQuery;
        if (it.moveToFirst()) {
            return it.getInt(it.getColumnIndex(KEY_INTOOK));
        }

        return 0;
    }

    public final int addIntook(String date, int selectedOption) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_INTOOK, getIntook(date) + selectedOption);
        int response = db.update(TABLE_NAME, contentValues, KEY_DATE + " = ?", new String[]{date});
        db.close();
        return response;
    }

   
    public final int checkExistance(String date) {
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT ");
        selectQuery.append(KEY_INTOOK);
        selectQuery.append(" FROM ");
        selectQuery.append(TABLE_NAME);
        selectQuery.append(" WHERE ");
        selectQuery.append(KEY_DATE);
        selectQuery.append(" = ?");
        Closeable rawQuery = getReadableDatabase().rawQuery(selectQuery.toString(), new String[]{date});

        Cursor it = (Cursor) rawQuery;
        if (it.moveToFirst()) {
            int count = it.getCount();
            return count;
        }

        return 0;
  }

    public final Cursor getAllStats() {
        return getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public final int updateTotalIntake(String date, int totalintake) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TOTAL_INTAKE, totalintake);

        int response = db.update(TABLE_NAME, contentValues, KEY_DATE + " = ?", new String[]{date});
        db.close();
        return response;
    }
}
