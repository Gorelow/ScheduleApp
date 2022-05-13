package com.example.firstapplication;

import android.view.View;
import android.widget.TextView;

import com.example.firstapplication.databinding.ActivityTwoBinding;
import com.example.firstapplication.model.CellAdapter;

class ActivityTwoo {

    private ActivityTwoBinding binding;
    private CellAdapter cellAdapter;


    TextView mTextView;
    View old;
    //DBHelper dbHelper = new DBHelper(this);

//    LinearLayout.LayoutParams normal;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //binding = ActivityTwoBinding.inflate(layoutInflater);
//        setContentView(R.layout.activity_two);
//
//        fill();
//    }
//
//    public void fill() {
//        int num = R.id.Lesson1Day1;
//        int k=8;
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//
//        Cursor cursor = database.query(DBHelper.NAME_TABLE_SCHEDULE,null,null,null,null, null,null);
//        if (cursor.moveToFirst()) {
//            int idIndex = cursor.getColumnIndex(DBHelper.KEY_DAY);
//            int startIndex = cursor.getColumnIndex(DBHelper.KEY_LESSON_TIME);
//            int subjectIndex = cursor.getColumnIndex(DBHelper.KEY_SUBJECT); // предмет
//            int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE); // тип занятия
//            int placeIndex = cursor.getColumnIndex(DBHelper.KEY_PLACE); // кабинет
//            do {
//                mTextView = (TextView) findViewById(num + cursor.getInt(idIndex) + cursor.getInt(startIndex)*6);
//                mTextView.setText(cursor.getString(subjectIndex)+"\n"+cursor.getString(typeIndex)+"\n"+cursor.getString(placeIndex));
//                Log.d("mLog","" + cursor.getInt(idIndex) + " " + cursor.getInt(startIndex));
//            } while (cursor.moveToNext());
//        }
//        else Log.d("mLog", "0 rows");
//
//        cursor.close();
//
//        dbHelper.close();
//    }
//
//    @SuppressLint("NonConstantResourceId")
//    public void onClick(View v) {
//        Intent intent;
//        int num = 2131296262;
//        switch (v.getId())   {
//            case R.id.buttonGoToMenu:
//                intent = new Intent(this,MainActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.button2:
//                intent = new Intent(this,ActivityFour.class);
//                startActivity(intent);
//                break;
//            default:
//                if (old != null)
//                {
//                    if (old == v)
//                    {
//                        ActivityThree.timeStampId = (v.getId() - num) / 6;
//                        ActivityThree.chosenDay = (v.getId() - num) % 6;
//                        intent = new Intent(this,ActivityThree.class);
//                        startActivity(intent);
//                    }
//                    else old.setBackground(null);
//                }
//                v.setBackgroundResource(R.drawable.back);
//                old = v;
//                break;
//        }
//
//    }
}