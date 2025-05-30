package com.coolapps.dailywater.target.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Closeable;


public final class SqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DATABASE_NAME";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_DATE = "KEY_DATE";
    private static final String KEY_ID = "KEY_ID";
    private static final String KEY_INTOOK = "KEY_INTOOK";
    private static final String KEY_TOTAL_INTAKE = "KEY_TOTAL_INTAKE";
    private static final String TABLE_STATS = "TABLE_STATS";
    private final Context context;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SqliteHelper(Context context2) {
        super(context2, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context2;
    }

    public final Context getContext() {
        return this.context;
    }



    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATS_TABLE = "CREATE TABLE " + TABLE_STATS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATE + " TEXT UNIQUE," + KEY_INTOOK + " INT," + KEY_TOTAL_INTAKE + " INT" + ")";
        if (db != null) {
            db.execSQL(CREATE_STATS_TABLE);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db == null) {
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        onCreate(db);
    }

    public final long addAll(String date, int intook, int totalintake) {
        if (checkExistance(date) != 0) {
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_INTOOK, Integer.valueOf(intook));
        values.put(KEY_TOTAL_INTAKE, Integer.valueOf(totalintake));
        SQLiteDatabase db = getWritableDatabase();
        long response = db.insert(TABLE_STATS, null, values);
        db.close();
        return response;
    }


    public final int getIntook(String date) {
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT ");
        selectQuery.append(KEY_INTOOK);
        selectQuery.append(" FROM ");
        selectQuery.append(TABLE_STATS);
        selectQuery.append(" WHERE ");
        selectQuery.append(KEY_DATE);
        selectQuery.append(" = ?");

        Closeable rawQuery = getReadableDatabase().rawQuery(selectQuery.toString(), new String[]{date});
        Cursor it = (Cursor) rawQuery;
        if (it.moveToFirst()) {
            int i = it.getInt(it.getColumnIndex(KEY_INTOOK));
            return i;
        }

        return 0;
    }

    public final int addIntook(String date, int selectedOption) {
        int intook = getIntook(date);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_INTOOK, Integer.valueOf(intook + selectedOption));
        String str = TABLE_STATS;
        int response = db.update(str, contentValues, KEY_DATE + " = ?", new String[]{date});
        db.close();
        return response;
    }

   
    public final int checkExistance(String date) {
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT ");
        selectQuery.append(KEY_INTOOK);
        selectQuery.append(" FROM ");
        selectQuery.append(TABLE_STATS);
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
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_STATS, null);
        return rawQuery;
    }

    public final int updateTotalIntake(String date, int totalintake) {
        int intook = getIntook(date);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TOTAL_INTAKE, Integer.valueOf(totalintake));
        String str = TABLE_STATS;
        int response = db.update(str, contentValues, KEY_DATE + " = ?", new String[]{date});
        db.close();
        return response;
    }
}
