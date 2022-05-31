package com.example.firstapplication.model

typealias WorkerListener = (subjects : List<Universal>) -> Unit

class WorkerService {

    private var workers = mutableListOf<Universal>()

    private var listeners = mutableSetOf<WorkerListener>()

    private val testingWorkers = arrayOf(   arrayOf("Горелов","Антон","Михайлович"),
                                            arrayOf("Иванов","Иван","Иванович"),
                                            arrayOf("Игнатьев","Евгений","Борисович"),
                                            arrayOf("Костин","Никита","Сергеевич"),
                                            arrayOf("Дворецкий","Денисс","Данилович"))

    init {
        workers = (0 until testingWorkers.count()).map { Universal(
            id = it.toLong(),
            itemInfo = testingWorkers[it][0] + " " + testingWorkers[it][1][0] + ". " + testingWorkers[it][2][0] + ". "
        ) }.toMutableList()
    }

    fun getWorkers(): List<Universal> {
        return workers
    }

    fun deleteWorker(worker : Universal) {
        val indexToDelete = workers.indexOfFirst { it.id == worker.id }
        if (indexToDelete != -1) {
            workers.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun changeWorker(index: Int, newWorker: Universal) {
        val indexToChange = workers.indexOfFirst { it.id == newWorker.id }
        workers[indexToChange].itemInfo = newWorker.itemInfo
        notifyChanges()
    }

    fun checkIfListenerIndexInRange(index: Int) : Boolean {
        if (index >= listeners.count()) return false
        return true
    }

    fun addListener(listener: WorkerListener) {
        listeners.add(listener)
        listener.invoke(workers)
    }

    fun removeListener(listener: WorkerListener) {
        listeners.remove(listener);
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(workers) }
    }
}