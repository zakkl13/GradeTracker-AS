package com.example.gradesapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.*;

/**
 * Created by SWAT Loaner on 2/10/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "GradeTrack.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void putClass(Class cls) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues classValues = new ContentValues();
        classValues.put(ClassContract.ClassEntry.COLUMN_NAME, cls.getName());
        classValues.put(ClassContract.ClassEntry.COLUMN_CREDITS, cls.getNumCrHrs());
        long classId;
        classId = db.insert(ClassContract.TABLE_NAME, null, classValues);
        cls.setId(classId);
        //nested for loops
        //first to put all of the categories into the database
        //and the inner one to put all of the assignments
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassContract.SQL_CREATE_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
