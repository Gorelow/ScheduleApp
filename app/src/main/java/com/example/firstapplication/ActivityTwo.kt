package com.example.firstapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapplication.databinding.ActivityTwoBinding
import com.example.firstapplication.model.CellAdapter
import com.example.firstapplication.model.CellService
import com.example.firstapplication.model.CellsListener

class ActivityTwo : AppCompatActivity() {

    private lateinit var binding: ActivityTwoBinding
    private lateinit var adapter: CellAdapter

    private val cellService : CellService
    get() = (applicationContext as App).cellService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_two)

        adapter = CellAdapter()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        cellService.AddListener(cellsListener)
    }


    //override fun OnDestroy() {
    //    super.OnDestroy()
    //    cellService.RemoveListener(cellsListener)
    //}

    private val cellsListener : CellsListener = {
        adapter.cells = it
    }
}