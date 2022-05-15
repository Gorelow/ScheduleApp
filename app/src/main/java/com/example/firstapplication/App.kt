package com.example.firstapplication

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.example.firstapplication.DB.DBHelper
import com.example.firstapplication.model.CellService
import com.example.firstapplication.model.UsersService

class App : Application() {
    var dbHelper = DBHelper(this)
    val cellService = CellService();
    val usersService = UsersService();

    fun getDataBase(): SQLiteDatabase {
        return dbHelper.writableDatabase
    }
    fun DBClose(){
        dbHelper.close()
    }
}