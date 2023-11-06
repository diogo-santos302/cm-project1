package com.example.mydaylogger.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "user_table")
data class UserInfo (
    @PrimaryKey(autoGenerate = true) //auto generates the number for id column
    val id: Int,
    val name: String,
    val age: Int,
    val height: Int,
    val weight: Double,
    val gender: String,
    val emergencyContact: Number
) {
}