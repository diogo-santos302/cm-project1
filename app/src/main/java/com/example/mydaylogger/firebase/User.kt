package com.example.mydaylogger.firebase

data class User(
    val name: String = "",
    val age: Int = 0,
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val gender: String = "",
    val firebaseToken: String = "",
    val caretaker: String = ""
)
