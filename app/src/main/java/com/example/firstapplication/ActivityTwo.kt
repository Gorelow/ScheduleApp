package com.example.firstapplication

import androidx.appcompat.app.AppCompatActivity
import com.example.firstapplication.databinding.ActivityTwoBinding
import com.example.firstapplication.databinding.ItemCellBinding
import com.example.firstapplication.model.CellAdapter
import com.example.firstapplication.model.CellService

class ActivityTwo : AppCompatActivity() {

    private lateinit var binding: ActivityTwoBinding
    private lateinit var adapter: CellAdapter

    private val cellService : CellService
    get() = (applicationContext as App).cellService
}