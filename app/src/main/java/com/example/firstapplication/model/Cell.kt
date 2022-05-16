package com.example.firstapplication.model

import com.example.firstapplication.Day
import com.example.firstapplication.TimeFrame


data class Cell (
    val id: Long,
    val day: Day,
    val timeFrame: Int,
    val content: CellContent
)