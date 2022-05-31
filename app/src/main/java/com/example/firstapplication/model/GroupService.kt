package com.example.firstapplication.model

typealias GroupListener = (subjects : List<Universal>) -> Unit

class GroupService {

    private var groups = mutableListOf<Universal>()

    private var listeners = mutableSetOf<GroupListener>()

    private val testingGroups = arrayOf("11a класс","2-41m","Утренняя","Вечерняя", "Базовая", "Продвинутая", "Особая", "Кампус A")

    init {
        groups = (0 until testingGroups.count()).map { Universal(
            id = it.toLong(),
            itemInfo = testingGroups[it]
        ) }.toMutableList()
    }

    fun getGroups(): List<Universal> {
        return groups
    }

    fun deleteGroups(group : Universal) {
        val indexToDelete = groups.indexOfFirst { it.id == group.id }
        if (indexToDelete != -1) {
            groups.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun changeGroup(index: Int, newGroup: Universal) {
        val indexToChange = groups.indexOfFirst { it.id == newGroup.id }
        groups[indexToChange].itemInfo =  newGroup.itemInfo
        notifyChanges()
    }

    fun checkIfListenerIndexInRange(index: Int) : Boolean {
        if (index >= listeners.count()) return false
        return true
    }

    fun addListener(listener: GroupListener) {
        listeners.add(listener)
        listener.invoke(groups)
    }

    fun removeListener(listener: GroupListener) {
        listeners.remove(listener);
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(groups) }
    }
}