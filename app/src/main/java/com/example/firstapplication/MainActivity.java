package com.example.firstapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapplication.DB.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static int UserType = -1;

    TextView textView;
    Button button1;
    Button button2;

    DBHelper dbHelper = new DBHelper(this);
    EditText etName, etEmail;

    static String[][] OptionValues = new String[][] {
            new String[]{"Расписание","Задание","Подписаться на группу"},
            new String[]{"Расписание","Задание","Изменить группу"},
            new String[]{"Преподаватели","Занятия","Группы"}};

    static Class[][] OptionActivities= new Class[][] {
            new Class[]{ActivityTwo.class,ActivityFour.class/*, НУЖЕН КЛАСС ДЛЯ ГРУПП*/},
            new Class[]{ActivityTwo.class,ActivityFour.class /*,"НУЖЕН КЛАСС ДЛЯ ГРУПП"*/},
            new Class[]{/*НУЖЕН КЛАСС ДЛЯ УЧИТЕЛЕЙ*//*,"НУЖЕН КЛАСС ДЛЯ ПРЕДМЕТОВ"*//*,"НУЖЕН КЛАСС ДЛЯ ГРУПП"*/}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        button1 = (Button) findViewById(R.id.option1);
        button2 = (Button) findViewById(R.id.option2);
        dbHelper = new DBHelper(this);

        checkOnEmpty();
        setOptionNames();


        dbHelper.close();
    }

    public void setOptionNames(){
        ((Button) findViewById(R.id.option1)).setText(OptionValues[UserType][0]);
        ((Button) findViewById(R.id.option2)).setText(OptionValues[UserType][1]);
        ((Button) findViewById(R.id.option3)).setText(OptionValues[UserType][2]);
    }


    public void checkOnEmpty() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        Cursor cursor = database.query(DBHelper.NAME_TABLE_TIMESTAMPS,null,null,null,null, null,null);
        if (!cursor.moveToFirst()){
            int startH = 8;
            int startM = 0;

            for (int i=0;i<6;i++) {
                contentValues.put(DBHelper.KEY_START, startH + ":" + (startM<10 ? startM + "0" : startM));
                startH++;
                startM += 35;
                if (startM >= 60){
                    startH++;
                    startM -= 60;
                }
                contentValues.put(DBHelper.KEY_END, startH + ":" + (startM<10 ? startM + "0" : startM));

                database.insert(DBHelper.NAME_TABLE_TIMESTAMPS, null, contentValues);

                contentValues.clear();

                if (i == 2) startM += 30;
                startM += 15;

                if (startM >= 60){
                    startH++;
                    startM -= 60;
                }
            }
        }
        cursor.close();

        Log.d("mLog", "Progress");
        Cursor cursor1 = database.query(DBHelper.TABLE_DAYS,null,null,null,null, null,null);
        if (!cursor1.moveToFirst()){
            contentValues.put(DBHelper.KEY_VALUE,"Понедельник");
            database.insert(DBHelper.TABLE_DAYS, null, contentValues);
            contentValues.put(DBHelper.KEY_VALUE,"Вторник");
            database.insert(DBHelper.TABLE_DAYS, null, contentValues);
            contentValues.put(DBHelper.KEY_VALUE,"Среда");
            database.insert(DBHelper.TABLE_DAYS, null, contentValues);
            contentValues.put(DBHelper.KEY_VALUE,"Четверг");
            database.insert(DBHelper.TABLE_DAYS, null, contentValues);
            contentValues.put(DBHelper.KEY_VALUE,"Пятница");
            database.insert(DBHelper.TABLE_DAYS, null, contentValues);
            contentValues.put(DBHelper.KEY_VALUE,"Суббота");
            database.insert(DBHelper.TABLE_DAYS, null, contentValues);

            Log.d("mLog", "poggers");


            contentValues.clear();
        }
        cursor1.close();
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId())   {
            case R.id.option1:
                intent = new Intent(this, OptionActivities[UserType][0]);
                startActivity(intent);
                break;

            case R.id.option2:
                try {
                    intent = new Intent(this, OptionActivities[UserType][1]);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(this, "start doesn't work", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.option3:
                try {
                    intent = new Intent(this, OptionActivities[UserType][2]);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(this, "start doesn't work", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}