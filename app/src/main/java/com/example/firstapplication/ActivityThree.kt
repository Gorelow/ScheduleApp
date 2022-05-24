package com.example.firstapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstapplication.DB.DBHelper
import com.example.firstapplication.databinding.ActivityThreeBinding
import com.example.firstapplication.model.*

class ActivityThree : AppCompatActivity() {
    private lateinit var mTextView: TextView

    var dbHelper = DBHelper(this)
    var updateData = false

    private lateinit var binding: ActivityThreeBinding
    private lateinit var adapter: ColourAdapter

    lateinit var etSubject: EditText
    lateinit var etType: EditText
    lateinit var etPlace: EditText
    lateinit var etTeacher: EditText
    lateinit var etDay: EditText
    lateinit var etStart: EditText
    lateinit var etEnd: EditText
    lateinit var etNotification: EditText
    private val colourService: ColourService
        get() = (applicationContext as App).colourService

    private val cellsService: CellsService
        get() = (applicationContext as App).cellsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ColourAdapter(object :  ColourActionListener{
            override fun OnPick(colour: Colour) {
                // надо это подготовить для баазы данных, но пока этого нет
                pickedColour = android.graphics.Color.argb(255,colour.red, colour.green, colour.blue)
                findViewById<View>(R.id.colors).setBackgroundColor(pickedColour)
            }
        })

        try {
            //mTextView = findViewById<View>(R.id.text) as TextView
            etSubject = findViewById<View>(R.id.inputSubject2) as EditText
            etType = findViewById<View>(R.id.inputType) as EditText
            etPlace = findViewById<View>(R.id.inputPlace) as EditText
            etTeacher = findViewById<View>(R.id.inputTeacher) as EditText
            etDay = findViewById<View>(R.id.inputDay) as EditText
            etStart = findViewById<View>(R.id.inputStartTime) as EditText
            etEnd = findViewById<View>(R.id.inputEndTime) as EditText
            // пока нет ничего так как нет соответсвующего поля
        // etNotification = findViewById<View>(R.id.inputEndTime) as EditText
        } catch ( e : Exception) {
            Toast.makeText(this, "starting init doesn't work", Toast.LENGTH_SHORT).show()
        }
        try {
            loadTimeStamp()
            checkInDB()
        } catch (e : Exception){
            Toast.makeText(this, "advanced init doesn't work", Toast.LENGTH_SHORT).show()
        }

        val layoutManager = GridLayoutManager(this,6)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter


        colourService.addListener(colourListener)
    }

    private val colourListener : ColourListener = {
        adapter.colours = it
    }

    override fun onDestroy() {
        super.onDestroy()
        colourService.removeListener(colourListener)
    }

    fun loadTimeStamp() {
        val chosenTimeframe = TimeFrame.BASE_TIMEFRAMES[timeStampPiked]
        FillTextView(etStart, chosenTimeframe._start._timeInString)
        FillTextView(etEnd, chosenTimeframe._end._timeInString)
        FillTextView(etDay, chosenDay.toString())
    }

    fun checkInDB() {
        val database = dbHelper.writableDatabase
        val cursor = database.query(DBHelper.NAME_TABLE_SCHEDULE, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            val oneIndex = cursor.getColumnIndex(DBHelper.KEY_SUBJECT)
            val twoIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE)
            val threeIndex = cursor.getColumnIndex(DBHelper.KEY_PLACE)
            val fourIndex = cursor.getColumnIndex(DBHelper.KEY_TEACHER)
            val forCheckOne = cursor.getColumnIndex(DBHelper.KEY_DAY)
            val forCheckTwo = cursor.getColumnIndex(DBHelper.KEY_LESSON_TIME)
            mTextView = findViewById<View>(R.id.inputDay) as TextView
            do {
                if (cursor.getString(forCheckOne) == chosenDay.toString() && cursor.getInt(forCheckTwo) == timeStampPiked) {
                    FillTextView(etSubject, cursor.getString(oneIndex))
                    FillTextView(etType, cursor.getString(twoIndex))
                    FillTextView(etPlace, cursor.getString(threeIndex))
                    FillTextView(etTeacher, cursor.getString(fourIndex))
                    findViewById<View>(R.id.colors).setBackgroundColor(pickedColour)
                    updateData = true
                    findViewById<View>(R.id.del_input_button).visibility = View.VISIBLE
                    break
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        dbHelper.close()
    }

    fun FillTextView(textView: TextView, value: String) {
        textView.text = value
    }


    fun DaySwapper(s1: String?): Int {
        return when (s1) {
            "Понедельник" -> 1
            "Вторник" -> 2
            "Среда" -> 3
            "Четверг" -> 4
            "Пятница" -> 5
            "Суббота" -> 6
            else -> {
                Log.d("mLog", "ОШИБОЧКА")
                -1
            }
        }
    }

    fun putDataInDirectory(database : SQLiteDatabase ,tableName : String, text: EditText) {
        val textValue = text.text.toString()
        val contentValues = ContentValues()
        contentValues.put(DBHelper.KEY_VALUE, textValue)
        database.insert(DBHelper.TABLE_SUBJECTS, null, contentValues)
        contentValues.clear()
    }

    fun fillContentValues(contentValues: ContentValues, keys: Array<String>, contentArray: Array<EditText>) {
        for (i in 0..keys.count()) {
            contentValues.put(keys[i],contentArray[i].text.toString())
        }
    }

    @SuppressLint("NonConstantResourceId")
    fun onClick(v: View) {
        val intent: Intent
        when (v.id) {
            R.id.buttonBack -> {
                intent = Intent(this, ActivityTwo::class.java)
                startActivity(intent)
            }
            R.id.buttonSave -> if (etSubject.text.isNotEmpty() &&
                etType.text.isNotEmpty() &&
                etPlace.text.isNotEmpty() &&
                etTeacher.text.isNotEmpty()
            ) {
                val database = dbHelper.writableDatabase
                val contentValues = ContentValues()
                val editTexts = arrayOf<EditText>(etSubject, etType, etPlace, etTeacher, etDay)
                val directories = arrayOf<String>(DBHelper.TABLE_SUBJECTS, DBHelper.NAME_TABLE_TYPES, DBHelper.TABLE_PLACES, DBHelper.TABLE_TEACHERS)
                val keys = arrayOf<String>(DBHelper.KEY_SUBJECT, DBHelper.KEY_TYPE, DBHelper.KEY_PLACE, DBHelper.KEY_TEACHER, DBHelper.KEY_DAY)

                try {
                    // сначала записать данные в отдельные таблицы, затем в одну большую
                    for (i in 0..3) {
                        putDataInDirectory(database, directories[i], editTexts[i])
                    }
                    //COLUMN_ID, COLUMN_DAY, COLUMN_LESSON_TIME, COLUMN_SUBJECT, COLUMN_TYPE, COLUMN_TEACHER, COLUMN_PLACE, COLUMN_COLOR, COLUMN_NOTIFICATION

                    for (i in 0 until keys.count()) {
                        contentValues.put(keys[i], editTexts[i].text.toString())
                    }
                    //fillContentValues(contentValues,keys,editTexts)
                    contentValues.put(DBHelper.KEY_LESSON_TIME, timeStampPiked)
                    contentValues.put(DBHelper.KEY_NOTIFICATION, 0)
                    contentValues.put(DBHelper.KEY_COLOR, pickedColour)
                    try {
                        if (updateData) database.update(
                            DBHelper.NAME_TABLE_SCHEDULE,
                            contentValues,
                            DBHelper.KEY_LESSON_TIME + "= " + timeStampPiked + " AND " + DBHelper.KEY_DAY + "= " + etDay.text.toString(),
                            null
                        ) else database.insert(DBHelper.NAME_TABLE_SCHEDULE, null, contentValues)
                        dbHelper.close()
                        intent = Intent(this, ActivityTwo::class.java)
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(this, "database insertion problem", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "content value input problem", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.delDay, R.id.delEndTime, R.id.delPlace, R.id.delStartTime, R.id.delSubject2, R.id.delTeacher, R.id.delType -> {
                mTextView = findViewById<View>(v.id) as TextView
                mTextView.text = ""
            }
            R.id.del_input_button -> {
                try {
                    val database = dbHelper.writableDatabase
                    database.delete(
                        DBHelper.NAME_TABLE_SCHEDULE,
                        DBHelper.KEY_LESSON_TIME + "= " + timeStampPiked + " AND " + DBHelper.KEY_DAY + "= '" + chosenDay + "'",
                        null
                    )
                    dbHelper.close()
                    cellsService.clearCell(chosenDay, timeStampPiked)
                } catch (e: Exception) {
                    Toast.makeText(this, "deletion wasn't successful", Toast.LENGTH_SHORT).show()
                }
                intent = Intent(this, ActivityTwo::class.java)
                startActivity(intent)
            }
            else -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        var timeStampPiked = -1
        var chosenDay: Day = Day.Sunday
        var pickedColour = 0
    }
}