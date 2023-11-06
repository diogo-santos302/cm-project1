package com.example.mydaylogger.app.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
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

    fun addUserToCaretaker(userPhoneNumber: String, caretakerPhoneNumber: String) {
        val pathToCaretakerUser = "caretakers/$caretakerPhoneNumber/users/$userPhoneNumber"
        database.child(pathToCaretakerUser).setValue(true)
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

    fun removeUserFromCaretaker(userPhoneNumber: String, caretakerPhoneNumber: String) {
        val pathToCaretakerUser = "caretakers/$caretakerPhoneNumber/users/$userPhoneNumber"
        database.child(pathToCaretakerUser).removeValue()
    }

    fun readUser(phoneNumber: String, callback: (User?) -> Unit) {
        val userReference = database.child("users").child(phoneNumber)
        userReference.readOnce {
            val user = it?.getValue<User>()
            callback(user)
        }
        Log.i(TAG, "readUser")
    }

    fun readCaretaker(phoneNumber: String, callback: (Caretaker?) -> Unit) {
        val caretakerReference = database.child("caretakers").child(phoneNumber)
        caretakerReference.readOnce {
            val caretaker = it?.getValue<Caretaker>()
            callback(caretaker)
        }
        Log.i(TAG, "readCaretaker")
    }

    private fun DatabaseReference.readOnce(onRead: (DataSnapshot?) -> Unit) {
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onRead(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                onRead(null)
            }
        })
    }
}