package com.example.firstapplication

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.example.firstapplication.DB.CloudBaseHelper
import com.example.firstapplication.DB.DBHelper
import com.example.firstapplication.model.CellsService
import com.example.firstapplication.model.ColourService
import com.example.firstapplication.model.UsersService

class App : Application() {
    var dbHelper = DBHelper(this)
    val cellsService = CellsService();
    val usersService = UsersService();
    val colourService = ColourService();
    val cloudBaseHelper = CloudBaseHelper();

    fun getDataBase(): SQLiteDatabase {
        return dbHelper.writableDatabase
    }
    fun DBClose(){
        dbHelper.close()
    }
}