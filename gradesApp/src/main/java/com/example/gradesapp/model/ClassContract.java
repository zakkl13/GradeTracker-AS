package com.example.gradesapp.model;

import android.provider.BaseColumns;

/**
 * Created by SWAT Loaner on 2/10/2015.
 */
public class ClassContract {

    public static final String TABLE_NAME ="class";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                                                + ClassContract.TABLE_NAME
                                                + " ("
                                                + ClassContract.ClassEntry._ID
                                                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                + ClassContract.ClassEntry.COLUMN_NAME
                                                + " TEXT,"
                                                + ClassContract.ClassEntry.COLUMN_CREDITS
                                                + " INTEGER);";

    public static abstract class ClassEntry implements BaseColumns {

                public static final String COLUMN_NAME = "name";
                public static final String COLUMN_CREDITS = "credits";
    }

    }
