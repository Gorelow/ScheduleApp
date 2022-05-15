package com.example.firstapplication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapplication.DB.DBHelper
import com.example.firstapplication.databinding.ActivityFourBinding
import com.example.firstapplication.model.User
import com.example.firstapplication.model.UsersListener
import com.example.firstapplication.model.UsersService

class ActivityFour : AppCompatActivity() {

    private lateinit var binding: ActivityFourBinding
    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (applicationContext as App).usersService

    private var mTextView: TextView? = null
    var dbHelper = DBHelper(this)

    lateinit var task_name: ArrayList<String>
    lateinit var task_subject: ArrayList<String>
    lateinit var task_deadline: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            //mTextView = findViewById<View>(R.id.text) as TextView
            fill()
        }
        catch (e : Exception) {
            Toast.makeText(this@ActivityFour, "mTextView init or fill doesn't work", Toast.LENGTH_SHORT).show()
        }
        super.onCreate(savedInstanceState)
        binding = ActivityFourBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_four)

        dbHelper = DBHelper(this)
        task_name = ArrayList<String>()
        task_subject = ArrayList<String>()
        task_deadline = ArrayList<String>()

        storeDataInArrays()

        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                usersService.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
                val database = dbHelper.writableDatabase
                var firstName = user.task
                database.delete(
                    DBHelper.NAME_TABLE_TASKS,
                    DBHelper.KEY_NAME + "= ?",
                    arrayOf(firstName)
                )
                dbHelper.close()
            }

            override fun onUserDetails(user: User) {
                Toast.makeText(this@ActivityFour, "User: ${user.subject}", Toast.LENGTH_SHORT).show()
            }

            override fun onUserEdit(user: User) {
                load(user.id.toInt())
            }
        })

        try {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        usersService.addListener(usersListener)
        }
        catch (e : Exception) {
            Toast.makeText(this@ActivityFour, "layoutManager init or recyclerView doesn't work", Toast.LENGTH_SHORT).show()
        }
    }

    fun storeDataInArrays() {
        val cursor = dbHelper.readAllData(DBHelper.NAME_TABLE_TASKS)
        if (cursor.count == 0) {
            Toast.makeText(this, "No data gathered", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                task_name.add(cursor.getString(0))
                task_subject.add(cursor.getString(1))
                task_deadline.add(cursor.getString(2))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }

    private val usersListener: UsersListener = {
        adapter.users = it
    }

    fun fill() {
        var i : Int = 1
        val database = dbHelper.writableDatabase
        val contentValues = ContentValues()
        val users : MutableList<User> = mutableListOf<User>()
        val cursor = database.query(DBHelper.NAME_TABLE_TASKS, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            val taskIndex = cursor.getColumnIndex(DBHelper.KEY_NAME)
            val deadlineIndex = cursor.getColumnIndex(DBHelper.KEY_DEADLINE)
            val subjectIndex = cursor.getColumnIndex(DBHelper.KEY_SUBJECT)
            do {
                users.add(User(
                    id = i.toLong(),
                    task =  cursor.getString(taskIndex),
                    subject = cursor.getString(subjectIndex),
                    deadline = cursor.getString(deadlineIndex)
                ))
                i++
                Log.d("mLog", "" + cursor.getString(subjectIndex))
            } while (cursor.moveToNext() )
        } else Log.d("mLog", "0 rows")

        try{
            usersService.load(users)
        } catch (e : Exception)
        {
            Toast.makeText(this@ActivityFour, "${e.message} error", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        dbHelper.close()


        //UsersService.load(users)
    }

    fun load(id : Int) {
        val database = dbHelper.writableDatabase
        val contentValues = ContentValues()
        val cursor = database.query(DBHelper.NAME_TABLE_TASKS, null, null, null, null, null, null)
        val intent: Intent
        ActivityFive.num = id
        if (cursor.move(ActivityFive.num)) {
            intent = Intent(this, ActivityFive::class.java)
            startActivity(intent)
        } else Log.d("mLog", "0 rows")
        cursor.close()
        dbHelper.close()
    }

    fun onClick(v: View) {
        val intent: Intent
        when (v.id) {
            R.id.buttonGoToMenu -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.buttonNewTask -> try {
                ActivityFive.num = -1
                intent = Intent(this, ActivityFive::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Seems to some kind of error", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val database = dbHelper.writableDatabase
                val contentValues = ContentValues()
                val cursor = database.query(DBHelper.NAME_TABLE_TASKS, null, null, null, null, null, null)
                if (cursor.move(ActivityFive.num)) {
                    intent = Intent(this, ActivityFive::class.java)
                    startActivity(intent)
                } else Log.d("mLog", "0 rows")
                cursor.close()
                dbHelper.close()
            }
        }
    }
}