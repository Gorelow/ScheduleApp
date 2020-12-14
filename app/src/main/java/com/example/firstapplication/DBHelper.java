package com.example.firstapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 15; // по нему будет понятно, стоит ли обнавлять бд
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CONSTANTS = "contacts";
    public static final String TABLE_SCHEDULE = "schedule";
    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_TYPES = "types";

    public static final String TABLE_TIMESTAMPS = "timestamps";
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String TABLE_TEACHERS = "teachers";
    public static final String TABLE_PLACES = "places";
    public static final String TABLE_DAYS = "days";

    public static final String KEY_ID = "_id";
    public static final String KEY_VALUE = "value";

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIL = "mail";

    public static final String KEY_START = "starttime";
    public static final String KEY_END = "endtime";

    //public static final String KEY_WEEK = "week";
    public static final String KEY_DAY = "day";
    public static final String KEY_LESSON_TIME = "lessonTime";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_TEACHER = "teacher";
    public static final String KEY_PLACE = "place";
    public static final String KEY_DEADLINE = "deadline";
    public static final String KEY_TYPE = "type";
    //public static final String KEY_NOTIFICATION = "notification";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_DAYS + "(" + KEY_ID
                + " integer primary key," + KEY_VALUE + " text" + ")");
        db.execSQL("create table " + TABLE_PLACES + "(" + KEY_ID
                + " integer primary key," + KEY_VALUE + " text" + ")");
        db.execSQL("create table " + TABLE_TEACHERS + "(" + KEY_ID
                + " integer primary key," + KEY_VALUE + " text" + ")");
        db.execSQL("create table " + TABLE_SUBJECTS + "(" + KEY_ID
                + " integer primary key," + KEY_VALUE + " text" + ")");
        db.execSQL("create table " + TABLE_TIMESTAMPS + "(" + KEY_ID
                + " integer primary key," + KEY_START + " text," + KEY_END + " text" + ")");
        db.execSQL("create table " + TABLE_TYPES + "(" + KEY_ID
                + " integer primary key," + KEY_VALUE + " text" + ")");

        db.execSQL("create table " + TABLE_CONSTANTS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text" + ")");

        db.execSQL("create table " + TABLE_SCHEDULE + "("
                + KEY_ID + " integer primary key,"
                + KEY_DAY + " integer,"
                + KEY_LESSON_TIME + " integer,"
                + KEY_SUBJECT + " text,"
                + KEY_TYPE + " text,"
                + KEY_TEACHER + " text,"
                + KEY_PLACE + " text,"
                + "foreign key (" + KEY_LESSON_TIME + ") REFERENCES " + TABLE_TIMESTAMPS + "(" + KEY_ID +"),"
                + "foreign key (" + KEY_SUBJECT + ") REFERENCES " + TABLE_SUBJECTS + "(" + KEY_VALUE +"),"
                + "foreign key (" + KEY_TEACHER + ") REFERENCES " + TABLE_TEACHERS + "(" + KEY_VALUE +"),"
                + "foreign key (" + KEY_PLACE + ") REFERENCES " + TABLE_PLACES + "(" + KEY_VALUE +"),"
                + "foreign key (" + KEY_TYPE + ") REFERENCES " + TABLE_TYPES + "(" + KEY_VALUE +")"+")");

        db.execSQL("create table " + TABLE_TASKS + "("
                + KEY_ID + " integer primary key,"
                + KEY_NAME + " text,"
                + KEY_SUBJECT + " text,"
                + KEY_DEADLINE + " text,"
                + "foreign key (" + KEY_SUBJECT + ") REFERENCES " + TABLE_SUBJECTS + "(" + KEY_VALUE +")" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_PLACES);
        db.execSQL("drop table if exists " + TABLE_TEACHERS);
        db.execSQL("drop table if exists " + TABLE_SUBJECTS);
        db.execSQL("drop table if exists " + TABLE_TIMESTAMPS);
        db.execSQL("drop table if exists " + TABLE_SCHEDULE);
        db.execSQL("drop table if exists " + TABLE_TASKS);
        db.execSQL("drop table if exists " + TABLE_TYPES);
        db.execSQL("drop table if exists " + TABLE_DAYS);


        db.execSQL("drop table if exists " + TABLE_CONSTANTS);

        onCreate(db);
    }

}
