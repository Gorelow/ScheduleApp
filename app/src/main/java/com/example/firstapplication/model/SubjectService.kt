package com.example.firstapplication.model

import com.example.firstapplication.TimeFrame
import javax.security.auth.Subject

typealias SubjectListener = (subjects : List<Universal>) -> Unit

class SubjectService {

    private var subjects = mutableListOf<Universal>()

    private var listeners = mutableSetOf<SubjectListener>()

    private val testingSubjects = arrayOf("Математика","Информатика","Геометрия","Технологии управления програмными проектами", "Разработка интеллектуальных систем",
                                            "Курс повышения квалификации", "Шахматы", "Занятия в бассейне")

    init {
        subjects = (0 until testingSubjects.count()).map { Universal(
            id = it.toLong(),
            itemInfo = testingSubjects[it]
        ) }.toMutableList()
    }

    fun getSubjects(): List<Universal> {
        return subjects
    }

    fun deleteSubject(subject : Universal) {
        val indexToDelete = subjects.indexOfFirst { it.id == subject.id }
        if (indexToDelete != -1) {
            subjects.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun changeSubject(index: Int, newSubject: Universal) {
        val indexToChange = subjects.indexOfFirst { it.id == newSubject.id }
        subjects[indexToChange].itemInfo =  newSubject.itemInfo
        notifyChanges()
    }

    fun checkIfListenerIndexInRange(index: Int) : Boolean {
        if (index >= listeners.count()) return false
        return true
    }

    fun addListener(listener: SubjectListener) {
        listeners.add(listener)
        listener.invoke(subjects)
    }

    fun removeListener(listener: SubjectListener) {
        listeners.remove(listener);
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(subjects) }
    }
}