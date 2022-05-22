package com.example.firstapplication.model

typealias ColourListener = (colour : List<Colour>) -> Unit

class ColourService {
    private var MaxColorValue = 255
    private var StepAmount = 4
    private var ColorStepValue = MaxColorValue / (StepAmount - 1)

    private var colours = mutableListOf<Colour>()

    private var listeners = mutableSetOf<ColourListener>()

    init {
        colours = (0.. StepAmount * StepAmount * StepAmount).map { Colour(
            id = it.toLong(),
            green = 0 + ColorStepValue * (it % StepAmount),
            red = (it / StepAmount) % StepAmount,
            blue = it / (StepAmount * StepAmount)
        ) }.toMutableList()
    }

    fun getCells(): List<Colour> {
        return colours
    }

    fun deleteCell(colour : Colour) {
        val indexToDelete = colours.indexOfFirst { it.id == colour.id }
        if (indexToDelete != -1) {
            colours.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun addListener(listener: ColourListener) {
        listeners.add(listener)
        listener.invoke(colours)
    }

    fun removeListener(listener: ColourListener) {
        listeners.remove(listener);
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(colours) }
    }
}