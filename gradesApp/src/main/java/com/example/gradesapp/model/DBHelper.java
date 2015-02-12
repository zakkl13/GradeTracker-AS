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

    /**
     * Puts a class into the database
     * @param cls
     */
    public void putClass(Class cls) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues classValues = new ContentValues();
        classValues.put(ClassContract.ClassEntry.COLUMN_NAME, cls.getName());
        classValues.put(ClassContract.ClassEntry.COLUMN_CREDITS, cls.getNumCrHrs());
        long classId;
        classId = db.insert(ClassContract.TABLE_NAME, null, classValues);
        cls.setId(classId);
    }

    public void updateClass(Class cls)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues newClassValues = new ContentValues();
        newClassValues.put(ClassContract.ClassEntry.COLUMN_NAME, cls.getName());
        newClassValues.put(ClassContract.ClassEntry.COLUMN_CREDITS, cls.getNumCrHrs());

        db.update(ClassContract.TABLE_NAME,
                        newClassValues,
                        ClassContract.ClassEntry._ID + "=?",
                        new String[] { String.valueOf(cls.getId())});

    }


    public void putCategory(Category cat, long classId)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues catValues = new ContentValues();
        catValues.put(CategoryContract.CategoryEntry.COLUMN_NAME, cat.getName());
        catValues.put(CategoryContract.CategoryEntry.COLUMN_WEIGHT, cat.getWeight());
        catValues.put(CategoryContract.CategoryEntry.COLUMN_CLASS_ID, classId);
        long catId;
        catId = db.insert(CategoryContract.TABLE_NAME, null, catValues);
        cat.setId(catId);
    }

    public void updateCategory(Category cat)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues newCatValues = new ContentValues();
        newCatValues.put(CategoryContract.CategoryEntry.COLUMN_NAME, cat.getName());
        newCatValues.put(CategoryContract.CategoryEntry.COLUMN_WEIGHT, cat.getWeight());

        db.update(ClassContract.TABLE_NAME,
                newCatValues,
                ClassContract.ClassEntry._ID + "=?",
                new String[] { String.valueOf(cat.getId())});

    }

    public void putAssignment(Assignment assgn, long catId)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues assgnValues = new ContentValues();
        assgnValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME, assgn.getName());
        assgnValues.put(AssignmentContract.AssignmentEntry.COLUMN_POINTS_REC, assgn.getPtsRecieved());
        assgnValues.put(AssignmentContract.AssignmentEntry.COLUMN_TOTAL_POINTS, assgn.getPtsRecieved());
        long assgnId;
        assgnId = db.insert(AssignmentContract.TABLE_NAME, null, assgnValues);
        assgn.setId(assgnId);
    }

    public void updateAssignment(Assignment assgn)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues newAssgnValues = new ContentValues();
        newAssgnValues.put(AssignmentContract.AssignmentEntry.COLUMN_NAME, assgn.getName());
        newAssgnValues.put(AssignmentContract.AssignmentEntry.COLUMN_POINTS_REC, assgn.getPtsRecieved());
        newAssgnValues.put(AssignmentContract.AssignmentEntry.COLUMN_POINTS_REC, assgn.getPtsRecieved());

        db.update(ClassContract.TABLE_NAME,
                newAssgnValues,
                ClassContract.ClassEntry._ID + "=?",
                new String[] { String.valueOf(assgn.getId())});

    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassContract.SQL_CREATE_TABLE);
        db.execSQL(CategoryContract.SQL_CREATE_TABLE);
        db.execSQL(AssignmentContract.SQL_CREATE_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
