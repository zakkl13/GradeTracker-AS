package com.example.gradesapp.model;

import android.provider.BaseColumns;

/**
 * Created by SWAT Loaner on 2/10/2015.
 */
public class CategoryContract {

    public static final String TABLE_NAME ="category";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + CategoryContract.TABLE_NAME + " ("
            + CategoryContract.CategoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CategoryContract.CategoryEntry.COLUMN_NAME + " TEXT,"
            + CategoryContract.CategoryEntry.COLUMN_WEIGHT + " DOUBLE,"
            + "FOREIGN KEY (" + CategoryContract.CategoryEntry.COLUMN_CLASS_ID
            + ") REFERENCES classes(" + ClassContract.ClassEntry._ID + "));";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CategoryContract.TABLE_NAME;

    public static abstract class CategoryEntry implements BaseColumns {

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_CLASS_ID = "class_id";
    }

}
