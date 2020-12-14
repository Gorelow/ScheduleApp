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

public class ActivityFive extends AppCompatActivity {

    private TextView mTextView;
    DBHelper dbHelper = new DBHelper(this);
    EditText etName, etSubject, etDeadline;
    public static int num = -1;
    boolean updateData = false;

    private String firstName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        mTextView = (TextView) findViewById(R.id.text);

        etSubject = (EditText) findViewById(R.id.inputSubject);
        etDeadline = (EditText) findViewById(R.id.inputDate);
        etName = (EditText) findViewById(R.id.inputTask);

        if (num >= 0) fill();
    }

    public void fill()
    {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_TASKS,null,null,null,null, null,null);
        if (cursor.move(num)) {
            int taskIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int deadlineIndex = cursor.getColumnIndex(DBHelper.KEY_DEADLINE);
            int subjectIndex = cursor.getColumnIndex(DBHelper.KEY_SUBJECT);

            firstName = cursor.getString(taskIndex);
            etName.setText(cursor.getString(taskIndex));
            etSubject.setText(cursor.getString(subjectIndex));
            etDeadline.setText(cursor.getString(deadlineIndex));

            updateData = true;
            findViewById(R.id.button).setVisibility(View.VISIBLE);
        }
        else Log.d("mLog", "0 rows");

        cursor.close();

        dbHelper.close();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())   {
            case R.id.buttonGoToMenu:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonBack:
                intent = new Intent(this,ActivityFour.class);
                startActivity(intent);
                break;
            case R.id.buttonSave:
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                ContentValues contentValues1 = new ContentValues();

                // сначала записать данные в отдельные таблицы, затем в одну большую
                String subject = etSubject.getText().toString();
                contentValues.put(DBHelper.KEY_VALUE, subject);
                database.insert(DBHelper.TABLE_SUBJECTS, null, contentValues);
                contentValues.clear();

                String name = etName.getText().toString();
                String deadline = etDeadline.getText().toString();

                contentValues1.put(DBHelper.KEY_SUBJECT, subject);
                contentValues1.put(DBHelper.KEY_NAME, name);
                contentValues1.put(DBHelper.KEY_DEADLINE, deadline);

                if (updateData)  database.delete(DBHelper.TABLE_TASKS, DBHelper.KEY_NAME + "= '"+ firstName + "'", null);
                database.insert(DBHelper.TABLE_TASKS, null, contentValues1);

                dbHelper.close();
                intent = new Intent(this,ActivityFour.class);
                startActivity(intent);
                break;
            case R.id.delTask:
                mTextView = (TextView) findViewById(R.id.inputTask);
                mTextView.setText("");
                break;
            case R.id.delSubject:
                mTextView = (TextView) findViewById(R.id.inputSubject);
                mTextView.setText("");
                break;
            case R.id.delDate:
                mTextView = (TextView) findViewById(R.id.inputDate);
                mTextView.setText("");
                break;
            case R.id.delNotify:
                mTextView = (TextView) findViewById(R.id.TextReminder);
                mTextView.setText("");
                break;
            case R.id.button:
                database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_TASKS, DBHelper.KEY_NAME + "= ?", new String[] {firstName});
                dbHelper.close();
                intent = new Intent(this,ActivityFour.class);
                startActivity(intent);
                break;
            default:
                intent = new Intent(this,ActivityTwo.class);
                startActivity(intent);
                break;
        }
    }
}