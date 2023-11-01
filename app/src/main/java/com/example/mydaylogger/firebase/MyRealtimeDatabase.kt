package com.example.mydaylogger.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

private const val TAG = "MyRealtimeDatabase"

object MyRealtimeDatabase {
    private val database = Firebase.database.reference

    fun createNewUser(
        phoneNumber: String,
        name: String,
        age: Int = 0,
        height: Double = 0.0,
        weight: Double = 0.0,
        gender: UserGender? = null,
        firebaseToken: String,
        caretakerPhoneNumber: String = ""
    ) {
        val user = User(
            name,
            age,
            height,
            weight,
            gender?.text ?: "",
            firebaseToken,
            caretakerPhoneNumber
        )
        database.child("users").child(phoneNumber).setValue(user)
        Log.d(TAG, "createNewUser")
    }

    fun updateUser(
        phoneNumber: String,
        name: String? = null,
        age: Int? = null,
        height: Double? = null,
        weight: Double? = null,
        gender: UserGender? = null,
        firebaseToken: String? = null,
        caretakerPhoneNumber: String? = null
    ) {
        val userReference = database.child("users").child(phoneNumber)
        name?.let { userReference.child("name").setValue(it) }
        age?.let { userReference.child("age").setValue(it) }
        height?.let { userReference.child("height").setValue(it) }
        weight?.let { userReference.child("weight").setValue(it) }
        gender?.let { userReference.child("gender").setValue(it.text) }
        firebaseToken?.let { userReference.child("firebaseToken").setValue(it) }
        caretakerPhoneNumber?.let { userReference.child("caretaker").setValue(it) }
        Log.d(TAG, "updateUser")
    }

    fun readUser(phoneNumber: String, callback: (User?) -> Unit) {
        val userReference = database.child("users").child(phoneNumber)
        val userListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "readUser:onDataChange")
                val user = snapshot.getValue<User>()
                callback(user)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "readUser:onCancelled", error.toException())
                callback(null)
            }
        }
        userReference.addListenerForSingleValueEvent(userListener)
    }
}