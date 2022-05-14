package com.example.firstapplication

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapplication.databinding.ActivityTwoBinding
import com.example.firstapplication.model.*
import java.time.LocalDate
import java.util.*


class ActivityTwo : AppCompatActivity() {

    private lateinit var binding: ActivityTwoBinding
    private lateinit var adapter: CellAdapter

    private val cellService: CellService
        get() = (applicationContext as App).cellService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_two)

        adapter = CellAdapter(object : CellActionListener {

            override fun onCellClick(cell: Cell) {
                TODO("Not yet implemented")
            }

            override fun onCellDoubleClick(cell: Cell) {
                TODO("Not yet implemented")
            }
        }

        )

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter


        cellService.AddListener(cellsListener)

        popupMenu()


    }

    private fun popupMenu() {
        val timeHelper = TimeHelper();
        findViewById<TextView>(R.id.special_up_text).text = timeHelper.CalculateDay(Calendar.getInstance().time).toString()


        val popupMenu = PopupMenu(applicationContext, findViewById(R.id.special_up_text))
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_edit -> {
                    Toast.makeText(this@ActivityTwo, "${cellService.getCells()[1].day} editing", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_clear -> {
                    Toast.makeText(this@ActivityTwo, "${cellService.getCells()[5].day} clearing", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_replace_with_new -> {
                    Toast.makeText(this@ActivityTwo, "${cellService.getCells().count()} replacing", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    Toast.makeText(this@ActivityTwo, "something is wrong here", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }

        findViewById<View>(R.id.special_up_text).setOnLongClickListener {
            try {
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
            true
        }
        Toast.makeText(this@ActivityTwo, "${cellService.getCells()[1].day} works", Toast.LENGTH_SHORT).show()
    }


    private val cellsListener : CellsListener = {
        adapter.cells = it
    }


    //TextView mTextView;
    //View old;
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