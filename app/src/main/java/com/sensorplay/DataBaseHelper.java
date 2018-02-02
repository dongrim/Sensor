package com.sensorplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 꾸꾸님 on 2018-01-27.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "sensorValue.db";
    public static final String TABLE_NAME = "sensorValue_table";
    public static final int DATABASE_VERSION = 1;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "x";
    public static final String COL_3 = "y";
    public static final String COL_4 = "z";
    public static final String COL_5 = "time";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, x INTEGER, y INTEGER, z INTEGER, time INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String x, String y, String z, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, x);
        contentValues.put(COL_3, y);
        contentValues.put(COL_4, z);
        contentValues.put(COL_5, time);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else
            return true;
    }

    public void deleteDbTable() {
        SQLiteDatabase db = this.getReadableDatabase();
//        db.delete(TABLE_NAME,null,null);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void dbopen() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.isOpen();
    }

    public void dbclose() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.close();
    }
}
