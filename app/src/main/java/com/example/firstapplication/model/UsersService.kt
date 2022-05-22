package com.example.firstapplication.model

import android.graphics.Color
import com.example.firstapplication.DB.DBHelper
import com.example.firstapplication.App
import com.example.firstapplication.MainActivity
import java.util.*

typealias UsersListener = (users: List<User>) -> Unit

class UsersService {

    private var users = mutableListOf<User>()

    private val listeners = mutableSetOf<UsersListener>()

    init {
        users = (1..100).map { User(
            id = it.toLong(),
            subject = " ",
            deadline = " ",
            task = " ",
            colour = Color.WHITE,
            notification = false
        )  }.toMutableList()
    }

    fun load(newUsers: List<User>) {
        users = (1..newUsers.count()).map { User(
            id = it.toLong(),
            subject = newUsers[it-1].subject,
            deadline = newUsers[it-1].deadline,
            task = newUsers[it-1].task,
            colour = newUsers[it-1].colour,
            notification = newUsers[it-1].notification
        )  }.toMutableList()
        notifyChanges()
    }

    fun getUsers(): List<User> {
        return users
    }

    fun deleteUser(user: User) {
        val indexToDelete = users.indexOfFirst { it.id == user.id }
        if (indexToDelete != -1) {
            users.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    fun changeUser(user: User)
    {
        val indexToChange = users.indexOfFirst { it.id == user.id }
        deleteUser(users[indexToChange])
        users.add(user)
        notifyChanges()
    }

    fun moveUser(user: User, moveBy: Int) {
        val oldIndex = users.indexOfFirst { it.id == user.id }
        if (oldIndex == -1) return
        val newIndex = oldIndex + moveBy
        if (newIndex < 0 || newIndex >= users.size) return
        Collections.swap(users, oldIndex, newIndex)
        notifyChanges()
    }

    fun addListener(listener: UsersListener) {
        listeners.add(listener)
        listener.invoke(users)
    }

    fun removeListener(listener: UsersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(users) }
    }

    companion object {
        fun load(users: List<User>) {
            load(users)
        }

        private val IMAGES = mutableListOf(
            "https://images.unsplash.com/photo-1600267185393-e158a98703de?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NjQ0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1579710039144-85d6bdffddc9?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Njk1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODE0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1620252655460-080dbec533ca?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzQ1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1613679074971-91fc27180061?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzUz&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1485795959911-ea5ebf41b6ae?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzU4&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1545996124-0501ebae84d0?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/flagged/photo-1568225061049-70fb3006b5be?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Nzcy&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1567186937675-a5131c8a89ea?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODYx&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800",
            "https://images.unsplash.com/photo-1546456073-92b9f0a8d413?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"
        )
    }
}