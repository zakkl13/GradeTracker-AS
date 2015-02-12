package com.example.gradesapp.model;

import android.provider.BaseColumns;

/**
 * Created by SWAT Loaner on 2/10/2015.
 */
public class AssignmentContract {

    public static final String TABLE_NAME ="assignment";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + AssignmentContract.TABLE_NAME + " ("
            + AssignmentContract.AssignmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AssignmentContract.AssignmentEntry.COLUMN_NAME + " TEXT,"
            + AssignmentContract.AssignmentEntry.COLUMN_POINTS_REC + " DOUBLE,"
            + AssignmentContract.AssignmentEntry.COLUMN_TOTAL_POINTS + " DOUBLE"
            + "FOREIGN KEY (" + AssignmentContract.AssignmentEntry.COLUMN_CATEGORY_ID
            + ") REFERENCES classes(" + CategoryContract.CategoryEntry._ID + "));";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AssignmentContract.TABLE_NAME;

    public static abstract class AssignmentEntry implements BaseColumns {

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TOTAL_POINTS = "total_points";
        public static final String COLUMN_POINTS_REC = "points_rec";
        public static final String COLUMN_CATEGORY_ID = "category_id";
    }

}
