package com.example.firstapplication.model

import com.example.firstapplication.Day
import com.example.firstapplication.TimeFrame
import java.util.*

typealias CellsListener = (cells : List<Cell>) -> Unit

class CellsService {

    private var cells = mutableListOf<Cell>()

    private var listeners = mutableSetOf<CellsListener>()

    init {
        cells = (0..42).map { Cell(
            id = it.toLong(),
            day = when((it) % 6) {
                0 -> Day.Monday
                1 -> Day.Tuesday
                2 -> Day.Wednesday
                3 -> Day.Thursday
                4 -> Day.Friday
                5 -> Day.Saturday
                else ->  Day.Sunday
            },
            timeFrame = it / 6,
            content = CellContent(3, 1)
        ) }.toMutableList()
    }

    fun getCells(): List<Cell> {
        return cells
    }

    fun deleteCell(cell: Cell) {
        val indexToDelete = cells.indexOfFirst { it.id == cell.id }
        if (indexToDelete != -1) {
            cells.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun moveCell(cell: Cell, newIndex: Int) {
        if (checkIfCellIndexInRange(newIndex)) return
        val oldIndex : Int = cells.indexOfFirst { it.id == cell.id }
        if (oldIndex == -1) return
        if (oldIndex == newIndex) return
        Collections.swap(cells, oldIndex, newIndex)

        notifyChanges()
    }

    fun changeCell(index: Int, newCell: Cell) {
        val indexToChange = cells.indexOfFirst { it.id == newCell.id }
        cells[indexToChange].content._content = newCell.content._content
        notifyChanges()
    }


    private fun checkIfCellIndexInRange(index: Int): Boolean {
        if ((index >= cells.count()) || (index < 0)) return false
        return true
    }

    fun checkIfListenerIndexInRange(index: Int) : Boolean {
        if (index >= listeners.count()) return false
        return true
    }

    fun addListener(listener: CellsListener) {
        listeners.add(listener)
        listener.invoke(cells)
    }

    fun removeListener(listener: CellsListener) {
        listeners.remove(listener);
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(cells) }
    }
}