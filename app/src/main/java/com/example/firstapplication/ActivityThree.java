package com.example.firstapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapplication.DB.DBHelper;

public class ActivityThree extends AppCompatActivity {

    private TextView mTextView;
    public static int timeStampId;
    public static int chosenDay;
    DBHelper dbHelper = new DBHelper(this);
    boolean updateData = false;

    EditText etSubject, etType, etPlace, etTeacher, etDay, etStart, etEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        mTextView = (TextView) findViewById(R.id.text);

        etSubject = (EditText) findViewById(R.id.inputSubject2);
        etType = (EditText) findViewById(R.id.inputType);
        etPlace = (EditText) findViewById(R.id.inputPlace);
        etTeacher = (EditText) findViewById(R.id.inputTeacher);
        etDay = (EditText) findViewById(R.id.inputDay);
        etStart = (EditText) findViewById(R.id.inputStartTime);
        etEnd = (EditText) findViewById(R.id.inputEndTime);

        loadTimeStamp();
        checkInDB();
    }

    public void loadTimeStamp(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.NAME_TABLE_TIMESTAMPS,null,null,null,null, null,null);
        if (cursor.move(timeStampId+1)) {
            int startIndex = cursor.getColumnIndex(DBHelper.KEY_START);
            int endIndex = cursor.getColumnIndex(DBHelper.KEY_END);

            mTextView = (TextView) findViewById(R.id.inputStartTime);
            mTextView.setText(cursor.getString(startIndex));
            mTextView = (TextView) findViewById(R.id.inputEndTime);
            mTextView.setText(cursor.getString(endIndex));
        }
        else Log.d("mLog", "0 rows");
        cursor = database.query(DBHelper.TABLE_DAYS,null,null,null,null, null,null);
        if (cursor.move(chosenDay+1)) {
            int startIndex = cursor.getColumnIndex(DBHelper.KEY_VALUE);

            mTextView = (TextView) findViewById(R.id.inputDay);
            mTextView.setText(cursor.getString(startIndex));
        }
        else Log.d("mLog", "0 rows");
        cursor.close();
        dbHelper.close();
    }

    public void checkInDB(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.NAME_TABLE_SCHEDULE,null,null,null,null, null,null);
        if (cursor.moveToFirst()) {
            int oneIndex = cursor.getColumnIndex(DBHelper.KEY_SUBJECT);
            int twoIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE);
            int threeIndex = cursor.getColumnIndex(DBHelper.KEY_PLACE);
            int fourIndex = cursor.getColumnIndex(DBHelper.KEY_TEACHER);

            int forCheckOne = cursor.getColumnIndex(DBHelper.KEY_DAY);
            int forCheckTwo = cursor.getColumnIndex(DBHelper.KEY_LESSON_TIME);

            mTextView = (TextView) findViewById(R.id.inputDay);

            do {
                Log.d("mLog", cursor.getString(forCheckOne) + " and " + cursor.getInt(forCheckTwo));

                if ((cursor.getInt(forCheckOne) == chosenDay) && (cursor.getInt(forCheckTwo) == timeStampId))
                {
                    Log.d("mLog", " -" + cursor.getString(forCheckOne) + "- and -" + mTextView.getText() + "-");
                    mTextView = (TextView) findViewById(R.id.inputSubject2);
                    mTextView.setText(cursor.getString(oneIndex));
                    mTextView = (TextView) findViewById(R.id.inputType);
                    mTextView.setText(cursor.getString(twoIndex));
                    mTextView = (TextView) findViewById(R.id.inputPlace);
                    mTextView.setText(cursor.getString(threeIndex));
                    mTextView = (TextView) findViewById(R.id.inputTeacher);
                    mTextView.setText(cursor.getString(fourIndex));
                    updateData = true;

                    findViewById(R.id.button3).setVisibility(View.VISIBLE);
                    break;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
    }

    public int DaySwapper(String s1)
    {
        switch (s1)
        {
            case "Понедельник": return 1;
            case "Вторник": return 2;
            case "Среда": return 3;
            case "Четверг": return 4;
            case "Пятница": return 5;
            case "Суббота": return 6;
            default:
                Log.d("mLog", "ОШИБОЧКА");
                return -1;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())   {
            case R.id.buttonBack:
                intent = new Intent(this,ActivityTwo.class);
                startActivity(intent);
                break;
            case R.id.buttonSave:
                if ((etSubject.getText().length() > 0) &&
                        (etType.getText().length() > 0) &&
                        (etPlace.getText().length() > 0) &&
                        (etTeacher.getText().length() > 0) &&
                        (etDay.getText().length() > 0) &&
                        (etStart.getText().length() > 0) &&
                        (etEnd.getText().length() > 0)) {
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    ContentValues contentValues1 = new ContentValues();

                    // сначала записать данные в отдельные таблицы, затем в одну большую
                    String subject = etSubject.getText().toString();
                    contentValues.put(DBHelper.KEY_VALUE, subject);
                    database.insert(DBHelper.TABLE_SUBJECTS, null, contentValues);
                    contentValues.clear();

                    String type = etType.getText().toString();
                    contentValues.put(DBHelper.KEY_VALUE, type);
                    database.insert(DBHelper.NAME_TABLE_TYPES, null, contentValues);
                    contentValues.clear();

                    String place = etPlace.getText().toString();
                    contentValues.put(DBHelper.KEY_VALUE, place);
                    database.insert(DBHelper.TABLE_PLACES, null, contentValues);
                    contentValues.clear();

                    String teacher = etTeacher.getText().toString();
                    contentValues.put(DBHelper.KEY_VALUE, teacher);
                    database.insert(DBHelper.TABLE_TEACHERS, null, contentValues);
                    contentValues.clear();

                    int day = DaySwapper(etDay.getText().toString());
                    String start = etStart.getText().toString();
                    String end = etEnd.getText().toString();

                    contentValues1.put(DBHelper.KEY_SUBJECT, subject);
                    contentValues1.put(DBHelper.KEY_TYPE, type);
                    contentValues1.put(DBHelper.KEY_PLACE, place);
                    contentValues1.put(DBHelper.KEY_TEACHER, teacher);
                    contentValues1.put(DBHelper.KEY_DAY, day);
                    contentValues1.put(DBHelper.KEY_LESSON_TIME, timeStampId);

                    if (updateData)  database.update(DBHelper.NAME_TABLE_SCHEDULE, contentValues1, DBHelper.KEY_LESSON_TIME + "= "+timeStampId + " AND " + DBHelper.KEY_DAY +"= " + day, null);
                    else  database.insert(DBHelper.NAME_TABLE_SCHEDULE, null, contentValues1);

                dbHelper.close();
                intent = new Intent(this,ActivityTwo.class);
                startActivity(intent);
                }
                break;
            case R.id.delDay:
                mTextView = (TextView) findViewById(R.id.inputDay);
                mTextView.setText("");
                break;
            case R.id.delEndTime:
                mTextView = (TextView) findViewById(R.id.inputEndTime);
                mTextView.setText("");
                break;
            case R.id.delPlace:
                mTextView = (TextView) findViewById(R.id.inputPlace);
                mTextView.setText("");
                break;
            case R.id.delStartTime:
                mTextView = (TextView) findViewById(R.id.inputStartTime);
                mTextView.setText("");
                break;
            case R.id.delSubject2:
                mTextView = (TextView) findViewById(R.id.inputSubject2);
                mTextView.setText("");
                break;
            case R.id.delTeacher:
                mTextView = (TextView) findViewById(R.id.inputTeacher);
                mTextView.setText("");
                break;
            case R.id.delType:
                mTextView = (TextView) findViewById(R.id.inputType);
                mTextView.setText("");
                break;
            case R.id.button3:
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.NAME_TABLE_SCHEDULE, DBHelper.KEY_LESSON_TIME + "= "+timeStampId + " AND " + DBHelper.KEY_DAY +"= " + chosenDay, null);
                dbHelper.close();
                intent = new Intent(this,ActivityFour.class);
                startActivity(intent);
                break;
            default:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}