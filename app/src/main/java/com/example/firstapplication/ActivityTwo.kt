package com.example.firstapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapplication.databinding.ActivityTwoBinding
import com.example.firstapplication.model.*

class ActivityTwo : AppCompatActivity() {

    private lateinit var binding: ActivityTwoBinding
    private lateinit var adapter: CellAdapter

    private val cellService
        get() = (applicationContext as App).cellService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_two)

        adapter = CellAdapter(object : CellActionListener {

            override fun onCellClick(cell: Cell) {
                TODO("Not yet implemented")
            }

            override fun onCellDoubleClick(cell: Cell) {
                TODO("Not yet implemented")
            }
        }

        )

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter



        cellService.AddListener(cellsListener)
        Toast.makeText(this@ActivityTwo, "${cellService.getCells()[3].day} this is working at least", Toast.LENGTH_SHORT).show()
    }


    //override fun OnDestroy() {
    //    super.OnDestroy()
    //    cellService.RemoveListener(cellsListener)
    //}

    private val cellsListener : CellsListener = {
        adapter.cells = it
    }
}