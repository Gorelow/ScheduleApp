package com.example.firstapplication.model

data class User(
    val id: Long,
    val task: String,
    val subject: String,
    val deadline: String,
    val colour: Int,
    val notification: Boolean
)