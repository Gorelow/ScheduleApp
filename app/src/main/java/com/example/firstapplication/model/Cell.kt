package com.example.firstapplication.model

import android.media.MediaSession2Service
import com.example.firstapplication.Day
import com.example.firstapplication.TimeFrame


data class Cell (
    val id: Long,
    val day: Day,
    val timeFrame: Int,
    val content: CellContent,
    val colour: Int,
    val notification: Boolean
)