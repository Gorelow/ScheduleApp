package com.example.firstapplication

import android.app.Application
import com.example.firstapplication.model.CellService

class App : Application() {
    val cellService = CellService();
}