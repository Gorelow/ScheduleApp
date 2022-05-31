package com.example.firstapplication.model

import android.graphics.Color
import com.example.firstapplication.Day
import com.example.firstapplication.TimeFrame
import java.util.*

typealias TimestampListener = (timestamps : List<Timestamp>) -> Unit

class TimestampService {

    private var timestamps = mutableListOf<Timestamp>()

    private var listeners = mutableSetOf<TimestampListener>()

    init {
        timestamps = (0 until TimeFrame.BASE_TIMEFRAMES.size).map { Timestamp(
            id = it.toLong(),
            timeFrame = TimeFrame(TimeFrame.BASE_TIMEFRAMES[it])
        ) }.toMutableList()
    }

    fun getTimestamps(): List<Timestamp> {
        return timestamps
    }

    fun deleteTimestamp(timestamp : Timestamp) {
        val indexToDelete = timestamps.indexOfFirst { it.id == timestamp.id }
        if (indexToDelete != -1) {
            timestamps.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun changeTimestamp(index: Int, newTimestamp: Timestamp) {
        val indexToChange = timestamps.indexOfFirst { it.id == newTimestamp.id }
        timestamps[indexToChange].timeFrame.set_start_and_end(newTimestamp.timeFrame)
        notifyChanges()
    }

    fun checkIfListenerIndexInRange(index: Int) : Boolean {
        if (index >= listeners.count()) return false
        return true
    }

    fun addListener(listener: TimestampListener) {
        listeners.add(listener)
        listener.invoke(timestamps)
    }

    fun removeListener(listener: TimestampListener) {
        listeners.remove(listener);
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(timestamps) }
    }
}