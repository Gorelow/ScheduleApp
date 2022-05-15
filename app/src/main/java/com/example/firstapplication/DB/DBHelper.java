package com.example.firstapplication.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 16; // по нему будет понятно, стоит ли обнавлять бд
    public static final String DATABASE_NAME = "contactDb";

    //region Названия всех таблиц в базе данных
    public static final String NAME_TABLE_CONSTANTS = "contacts";
    public static final String NAME_TABLE_SCHEDULE = "schedule";
    public static final String NAME_TABLE_TASKS = "tasks";
    public static final String NAME_TABLE_TYPES = "types";
    public static final String NAME_TABLE_TIMESTAMPS = "timestamps";

    //region Название таблиц-справочников
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String TABLE_TEACHERS = "teachers";
    public static final String TABLE_PLACES = "places";
    public static final String TABLE_DAYS = "days";
    //endregion

    //endregion

    //region все названия стобцов в игре
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
    //endregion

    // region все столбцы в прилложении Columns
    public static final DBTableColumn COLUMN_ID = new DBTableColumn(KEY_ID, DBDataType.integer, true);
    public static final DBTableColumn COLUMN_TEXT_VALUE = new DBTableColumn(KEY_VALUE, DBDataType.text);
    public static final DBTableColumn COLUMN_NAME = new DBTableColumn(KEY_NAME, DBDataType.text);
    public static final DBTableColumn COLUMN_MAIL = new DBTableColumn(KEY_MAIL, DBDataType.text);

    public static final DBTableColumn COLUMN_START = new DBTableColumn(KEY_START, DBDataType.text);
    public static final DBTableColumn COLUMN_END = new DBTableColumn(KEY_END, DBDataType.text);
    public static final DBTableColumn COLUMN_DAY = new DBTableColumn(KEY_DAY, DBDataType.text);
    public static final DBTableColumn COLUMN_LESSON_TIME = new DBTableColumn(KEY_LESSON_TIME, DBDataType.text);
    public static final DBTableColumn COLUMN_SUBJECT = new DBTableColumn(KEY_SUBJECT, DBDataType.text);
    public static final DBTableColumn COLUMN_TEACHER = new DBTableColumn(KEY_TEACHER, DBDataType.text);
    public static final DBTableColumn COLUMN_PLACE = new DBTableColumn(KEY_PLACE, DBDataType.text);
    public static final DBTableColumn COLUMN_DEADLINE = new DBTableColumn(KEY_DEADLINE, DBDataType.text);
    public static final DBTableColumn COLUMN_TYPE = new DBTableColumn(KEY_TYPE, DBDataType.text);

    public static final DBTableColumn[] COLUMNS_FOR_DIRECTORY = new DBTableColumn[] { COLUMN_ID, COLUMN_TEXT_VALUE};
    // endregion

    // region все таблицы-справочники
    public static final DBTable TABLE_DIRECTORY_DAYS = new DBTable(TABLE_DAYS, COLUMNS_FOR_DIRECTORY);
    public static final DBTable TABLE_DIRECTORY_PLACES = new DBTable(TABLE_PLACES, COLUMNS_FOR_DIRECTORY);
    public static final DBTable TABLE_DIRECTORY_TEACHERS = new DBTable(TABLE_TEACHERS, COLUMNS_FOR_DIRECTORY);
    public static final DBTable TABLE_DIRECTORY_SUBJECTS = new DBTable(TABLE_SUBJECTS, COLUMNS_FOR_DIRECTORY);
    public static final DBTable TABLE_DIRECTORY_TYPES = new DBTable(NAME_TABLE_TYPES, COLUMNS_FOR_DIRECTORY);
    // endregion

    //**Все табицы-справочники в БД на текущий момент*/
    public static final DBTable[] TABLES_DIRECTORY = new DBTable[] {TABLE_DIRECTORY_DAYS, TABLE_DIRECTORY_PLACES, TABLE_DIRECTORY_TEACHERS, TABLE_DIRECTORY_SUBJECTS, TABLE_DIRECTORY_TYPES};

    public static final DBTable TABLE_TIMESTAMPS = new DBTable(NAME_TABLE_TIMESTAMPS, new DBTableColumn[] {COLUMN_ID, COLUMN_START, COLUMN_END});
    public static final DBTable TABLE_CONSTANTS = new DBTable(NAME_TABLE_CONSTANTS, new DBTableColumn[] {COLUMN_ID, COLUMN_NAME, COLUMN_MAIL});

    public static final DBForeignKey FOREIGN_SCHEDULE_TIMESTAMPS_KEY_ID = new DBForeignKey(COLUMN_LESSON_TIME, TABLE_TIMESTAMPS, COLUMN_ID);
    public static final DBForeignKey FOREIGN_SCHEDULE_SUBJECTS_KEY_VALUE = new DBForeignKey(COLUMN_SUBJECT, TABLE_DIRECTORY_SUBJECTS, COLUMN_TEXT_VALUE);
    public static final DBForeignKey FOREIGN_SCHEDULE_TEACHERS_KEY_VALUE = new DBForeignKey(COLUMN_TEACHER, TABLE_DIRECTORY_TEACHERS, COLUMN_TEXT_VALUE);
    public static final DBForeignKey FOREIGN_SCHEDULE_PLACES_KEY_VALUE = new DBForeignKey(COLUMN_PLACE, TABLE_DIRECTORY_PLACES, COLUMN_TEXT_VALUE);
    public static final DBForeignKey FOREIGN_SCHEDULE_TYPES_KEY_VALUE = new DBForeignKey(COLUMN_TYPE, TABLE_DIRECTORY_TYPES, COLUMN_TEXT_VALUE);
    public static final DBForeignKey[] FOREIGN_SCHEDULE_KEYS = new DBForeignKey[] { FOREIGN_SCHEDULE_TIMESTAMPS_KEY_ID, FOREIGN_SCHEDULE_SUBJECTS_KEY_VALUE, FOREIGN_SCHEDULE_TEACHERS_KEY_VALUE, FOREIGN_SCHEDULE_PLACES_KEY_VALUE, FOREIGN_SCHEDULE_TYPES_KEY_VALUE};

    public static final DBForeignKey FOREIGN_TASKS_SUBJECTS_KEY_VALUE = new DBForeignKey(COLUMN_SUBJECT, TABLE_DIRECTORY_SUBJECTS, COLUMN_TEXT_VALUE);
    public static final DBForeignKey[] FOREIGN_TASKS_KEYS = new DBForeignKey[] {FOREIGN_TASKS_SUBJECTS_KEY_VALUE};

    public static final DBTable TABLE_SCHEDULE = new DBTable(NAME_TABLE_SCHEDULE, new DBTableColumn[] {COLUMN_ID, COLUMN_DAY, COLUMN_LESSON_TIME, COLUMN_SUBJECT, COLUMN_TYPE, COLUMN_TEACHER, COLUMN_PLACE}, FOREIGN_SCHEDULE_KEYS);
    public static final DBTable TABLE_TASKS = new DBTable(NAME_TABLE_TASKS, new DBTableColumn[] {COLUMN_ID, COLUMN_NAME, COLUMN_SUBJECT, COLUMN_DEADLINE}, FOREIGN_TASKS_KEYS);

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (DBTable directoryTable : TABLES_DIRECTORY) {
            createTable(db, directoryTable);
        }

        createTable(db, TABLE_TIMESTAMPS);
        createTable(db, TABLE_CONSTANTS);
        createTable(db, TABLE_SCHEDULE);
        createTable(db, TABLE_TASKS);
    }

    private void createTable(SQLiteDatabase db, DBTable table)
    {
        db.execSQL("create " + table.info());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_PLACES);
        db.execSQL("drop table if exists " + TABLE_TEACHERS);
        db.execSQL("drop table if exists " + TABLE_SUBJECTS);
        db.execSQL("drop table if exists " + NAME_TABLE_TIMESTAMPS);
        db.execSQL("drop table if exists " + NAME_TABLE_SCHEDULE);
        db.execSQL("drop table if exists " + NAME_TABLE_TASKS);
        db.execSQL("drop table if exists " + NAME_TABLE_TYPES);
        db.execSQL("drop table if exists " + TABLE_DAYS);


        db.execSQL("drop table if exists " + NAME_TABLE_CONSTANTS);

        onCreate(db);
    }

    public Cursor readAllData(String dataTableName) {
        String query = "SELECT * FROM " + dataTableName;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
}
