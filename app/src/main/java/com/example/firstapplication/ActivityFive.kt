package com.example.firstapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firstapplication.DB.DBHelper
import com.example.firstapplication.databinding.ActivityFiveBinding
import com.example.firstapplication.databinding.ActivityThreeBinding
import com.example.firstapplication.model.Colour
import com.example.firstapplication.model.ColourListener
import com.example.firstapplication.model.ColourService
import java.util.*

class ActivityFive : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    lateinit var mTextView: TextView
    var dbHelper = DBHelper(this)
    lateinit var etName: EditText

    companion object {
        @kotlin.jvm.JvmField
        var num: Int = -1
        var pickedColour = 0
    }

    private lateinit var binding: ActivityFiveBinding
    private lateinit var adapter: ColourAdapter

    public var updateData = false

    lateinit var etSubject : EditText
    lateinit var etDeadline : TextView

    private var firstName = ""

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0
    //comd+shift

    private val colourService: ColourService
        get() = (applicationContext as App).colourService

    private val colourListener : ColourListener = {
        adapter.colours = it
    }

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ColourAdapter(object :  ColourActionListener{
            override fun OnPick(colour: Colour) {
                // надо это подготовить для баазы данных, но пока этого нет
                pickedColour = android.graphics.Color.argb(255,colour.red, colour.green, colour.blue)
                findViewById<View>(R.id.recyclerView).setBackgroundColor(ActivityFive.pickedColour)
            }
        })

        try {
            mTextView = findViewById<View>(R.id.inputTask) as TextView
        }  catch (e : Exception) {
            Toast.makeText(this@ActivityFive, "problem with mTextView", Toast.LENGTH_SHORT).show()
        }
        try {
            etSubject = findViewById<View>(R.id.inputSubject) as EditText
        }  catch (e : Exception) {
            Toast.makeText(this@ActivityFive, "problem with etSubject", Toast.LENGTH_SHORT).show()
        }
        try {
            etDeadline = findViewById<View>(R.id.inputDate) as TextView
        }  catch (e : Exception) {
            Toast.makeText(this@ActivityFive, "problem with etDeadline", Toast.LENGTH_SHORT).show()
        }
        try {
            etName = findViewById<View>(R.id.inputTask) as EditText
        }  catch (e : Exception) {
            Toast.makeText(this@ActivityFive, "problem with etName", Toast.LENGTH_SHORT).show()
        }

        try {
            if (num >= 0) fill()
        } catch (e: Exception) {
            Toast.makeText(this@ActivityFive, "fill error ${e.message}", Toast.LENGTH_SHORT).show()
        }

        try {
            pickDate()
        } catch (e: Exception) {
            Toast.makeText(this@ActivityFive, "pickDate error ${e.message}", Toast.LENGTH_SHORT).show()
        }

        val layoutManager = GridLayoutManager(this,6)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter


        colourService.addListener(colourListener)
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()

        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)


    }

    private fun pickDate() {
        findViewById<View>(R.id.inputDate).setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this,this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(this, this, hour, minute, true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        var output : String = ""
        if (day<10) output +="0"
        output += "$day."
        if (month<10) output +="0"
        output += "$month.$year     "
        if (hourOfDay < 10) output+="0"
        output += "$hourOfDay:"
        if (minute < 10) output+="0"
        output += "$minute"

        etDeadline.text = output
    }

    fun fill() {
        val database = dbHelper.writableDatabase
        val cursor = database.query(DBHelper.NAME_TABLE_TASKS, null, null, null, null, null, null)
        if (cursor.move(num)) {
            val taskIndex = cursor.getColumnIndex(DBHelper.KEY_NAME)
            val deadlineIndex = cursor.getColumnIndex(DBHelper.KEY_DEADLINE)
            val subjectIndex = cursor.getColumnIndex(DBHelper.KEY_SUBJECT)
            firstName = cursor.getString(taskIndex)
            etName.setText(cursor.getString(taskIndex))
            etSubject.setText(cursor.getString(subjectIndex))
            etDeadline.setText(cursor.getString(deadlineIndex))
            updateData = true
            findViewById<View>(R.id.button).visibility = View.VISIBLE
        } else Log.d("mLog", "0 rows")
        cursor.close()
        dbHelper.close()
    }

    @SuppressLint("NonConstantResourceId")
    fun onClick(v: View) {
        val intent: Intent
        val database = dbHelper.writableDatabase
        when (v.id) {
            R.id.buttonGoToMenu -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.buttonBack -> {
                intent = Intent(this, ActivityFour::class.java)
                startActivity(intent)
            }
            R.id.buttonSave -> {
                val contentValues = ContentValues()
                val contentValues1 = ContentValues()

                // сначала записать данные в отдельные таблицы, затем в одну большую
                val subject: String = etSubject.getText().toString()
                contentValues.put(DBHelper.KEY_VALUE, subject)
                database.insert(DBHelper.TABLE_SUBJECTS, null, contentValues)
                contentValues.clear()
                val name = etName!!.text.toString()
                val deadline: String = etDeadline.getText().toString()
                contentValues1.put(DBHelper.KEY_SUBJECT, subject)
                contentValues1.put(DBHelper.KEY_NAME, name)
                contentValues1.put(DBHelper.KEY_DEADLINE, deadline)
                contentValues1.put(DBHelper.KEY_COLOR, pickedColour)
                contentValues1.put(DBHelper.KEY_NOTIFICATION, 1)
                if (updateData) database.delete(
                    DBHelper.NAME_TABLE_TASKS,
                    DBHelper.KEY_NAME + "= '" + firstName + "'",
                    null
                )
                database.insert(DBHelper.NAME_TABLE_TASKS, null, contentValues1)
                dbHelper.close()
                intent = Intent(this, ActivityFour::class.java)
                startActivity(intent)
            }
            R.id.delTask, R.id.delSubject -> {
                mTextView = v as TextView
                mTextView.text = ""
            }
            R.id.button -> {
                database.delete(
                    DBHelper.NAME_TABLE_TASKS,
                    DBHelper.KEY_NAME + "= ?",
                    arrayOf(firstName)
                )
                dbHelper.close()
                intent = Intent(this, ActivityFour::class.java)
                startActivity(intent)
            }
            else -> {
                intent = Intent(this, ActivityTwo::class.java)
                startActivity(intent)
            }
        }
    }


}