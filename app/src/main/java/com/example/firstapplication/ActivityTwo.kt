package com.example.firstapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstapplication.DB.DBHelper
import com.example.firstapplication.databinding.ActivityTwoBinding
import com.example.firstapplication.model.*
import java.util.*


class ActivityTwo : AppCompatActivity() {

    private lateinit var binding: ActivityTwoBinding
    private lateinit var adapter: CellsAdapter

    var dbHelper = DBHelper(this)

    private val cellsService: CellsService
        get() = (applicationContext as App).cellsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CellsAdapter(object : CellActionListener {
            override fun OnCellEdit(cell: Cell) {
                try {
                    load(cell)
                }
                catch (e : Exception)
                {
                    Toast.makeText(this@ActivityTwo, "We tried", Toast.LENGTH_SHORT).show()
                }
            }

            override fun OnCellClear(cell: Cell) {
                Toast.makeText(this@ActivityTwo, "${cell.id}", Toast.LENGTH_SHORT).show()
            }

            override fun OnCellReplaceWithNew(cell: Cell) {
                TODO("Not yet implemented")
            }
        })

        val layoutManager = GridLayoutManager(this,6)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter


        cellsService.addListener(cellsListener)

        popupMenu()
        fill()

    }

    private fun popupMenu() {
        val timeHelper = TimeHelper();
        findViewById<TextView>(R.id.special_up_text).text = timeHelper.CalculateDay(Calendar.getInstance().time).toString()


        val popupMenu = PopupMenu(applicationContext, findViewById(R.id.special_up_text))
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_edit -> {
                    Toast.makeText(this@ActivityTwo, "Pressed editing", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_clear -> {
                    Toast.makeText(this@ActivityTwo, "Pressed clearing", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_replace_with_new -> {
                    Toast.makeText(this@ActivityTwo, "Pressed replacing", Toast.LENGTH_SHORT).show()
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
    }


    fun load(cell: Cell) {
        try {
            ActivityThree.timeStampPiked = cell.timeFrame;
            ActivityThree.chosenDay = cell.day;
        }
        catch (e : Exception) {
            Toast.makeText(this@ActivityTwo, "Can't start initialisation", Toast.LENGTH_SHORT).show()
        }
        try {
            intent = Intent(this, ActivityThree::class.java);
            startActivity(intent)
        } catch (e : Exception) {
            Toast.makeText(this@ActivityTwo, "Can't start intent", Toast.LENGTH_SHORT).show()
        }
    }

    private val cellsListener : CellsListener = {
        adapter.cells = it
    }


    override fun onDestroy() {
        super.onDestroy()
        cellsService.removeListener(cellsListener)
    }

    fun onClick(view: View) {
        var intent: Intent
        when(view.id) {
            R.id.buttonGoToMenu -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else -> {
                //ActivityThree.timeStampId = (v.getId() - num) / 6
                //ActivityThree.chosenDay = (v.getId() - num) % 6
                intent = Intent(this, ActivityThree::class.java)
                startActivity(intent)
            }
        }
    }

    fun fill() {
        var rowIndex = 0
        var columnIndex = 0
        val k = 8
        val database = dbHelper.writableDatabase
        //val contentValues = ContentValues()
        val cursor = database.query(DBHelper.NAME_TABLE_SCHEDULE, null, null, null, null, null, null)
        //Toast.makeText(this@ActivityTwo, "${cursor.count} inputs form database", Toast.LENGTH_SHORT).show()
        if (cursor.moveToFirst()) {
            try {
            columnIndex = cursor.getColumnIndex(DBHelper.KEY_DAY)
            rowIndex = cursor.getColumnIndex(DBHelper.KEY_LESSON_TIME)
            val subjectIndex: Int = cursor.getColumnIndex(DBHelper.KEY_SUBJECT) // предмет
            val typeIndex: Int = cursor.getColumnIndex(DBHelper.KEY_TYPE) // тип занятия
            val placeIndex: Int = cursor.getColumnIndex(DBHelper.KEY_PLACE) // кабинет
            do {
                val content = CellContent(3,1)
                content.set_content(0,0, cursor.getString(subjectIndex))
                content.set_content(1,0, cursor.getString(typeIndex))
                content.set_content(2,0, cursor.getString(placeIndex))
                val columnValue = cursor.getString(columnIndex)
                val rowValue = cursor.getInt(rowIndex)
                val dayNumber = Day.values().indexOfFirst { it.toString() == columnValue }
                //Toast.makeText(this@ActivityTwo, "column $dayNumber, row $columnValue", Toast.LENGTH_SHORT).show()
                val cell = Cell(
                    id = (rowValue * 6 + dayNumber).toLong(),
                    day = Day.values()[dayNumber],
                    timeFrame = rowIndex,
                    content = content
                )
                try {
                    cellsService.changeCell(cell.id.toInt(), cell)
                } catch (e : Exception) {
                    Toast.makeText(this@ActivityTwo, "column $columnValue, row $rowValue", Toast.LENGTH_SHORT).show()
                }

            } while (cursor.moveToNext())
            } catch (e : Exception) {
                Toast.makeText(this@ActivityTwo, "Can't load the thing", Toast.LENGTH_SHORT).show()
            }
        } else
        cursor.close()
        dbHelper.close()
    }
    //TextView mTextView;
    //View old;

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