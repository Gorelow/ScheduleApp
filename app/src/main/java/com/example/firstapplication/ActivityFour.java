package com.example.firstapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapplication.DB.DBHelper;

public class ActivityFour extends AppCompatActivity {

    private TextView mTextView;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        mTextView = (TextView) findViewById(R.id.text);

        fill();
    }

    public void fill() {
        int num = R.id.textTaskName1; // 42, 34
        int i = 0;
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        Cursor cursor = database.query(DBHelper.NAME_TABLE_TASKS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int taskIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int deadlineIndex = cursor.getColumnIndex(DBHelper.KEY_DEADLINE);
            int subjectIndex = cursor.getColumnIndex(DBHelper.KEY_SUBJECT);
            do {
                mTextView = (TextView) findViewById(num + i);
                mTextView.setText(cursor.getString(taskIndex));
                mTextView = (TextView) findViewById(num - 7 + i);
                mTextView.setText(cursor.getString(subjectIndex));
                mTextView = (TextView) findViewById(num - 16 + i);
                mTextView.setText(cursor.getString(deadlineIndex));
                i++;
                Log.d("mLog", "" + cursor.getString(subjectIndex));
            } while (cursor.moveToNext());
        } else Log.d("mLog", "0 rows");

        cursor.close();

        dbHelper.close();
    }

    public void onClick(View v) {
        int num = R.id.textTaskName1;
        Intent intent;
        switch (v.getId()) {
            case R.id.buttonGoToMenu:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonNewTask:
                try {
                    ActivityFive.num = -1;
                    intent = new Intent(this, ActivityFive.class);
                    startActivity(intent);
                } catch(Exception e) {
                    Toast.makeText(this, "Seems to some kind of error", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                ActivityFive.num = v.getId() - num + 1;
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                Cursor cursor = database.query(DBHelper.NAME_TABLE_TASKS, null, null, null, null, null, null);
                if (cursor.move(ActivityFive.num)) {
                    intent = new Intent(this, ActivityFive.class);
                    startActivity(intent);
                } else Log.d("mLog", "0 rows");

                cursor.close();

                dbHelper.close();
                break;
        }

    }
}