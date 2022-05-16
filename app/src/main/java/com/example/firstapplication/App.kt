package com.example.firstapplication

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.example.firstapplication.DB.DBHelper
import com.example.firstapplication.model.CellsService
import com.example.firstapplication.model.UsersService

class App : Application() {
    var dbHelper = DBHelper(this)
    val cellsService = CellsService();
    val usersService = UsersService();

    fun getDataBase(): SQLiteDatabase {
        return dbHelper.writableDatabase
    }
    fun DBClose(){
        dbHelper.close()
    }
}