package com.example.mydaylogger.app.data

import android.util.Log
import com.example.mydaylogger.app.firebase.User
import com.example.mydaylogger.app.firebase.MyRealtimeDatabase
import com.example.mydaylogger.app.firebase.UserGender

private const val TAG = "DatabaseManager"

class DatabaseManager(private val databaseInstance: MyRealtimeDatabase) {
    fun addNewUser(
        phoneNumber: String,
        name: String,
        age: Int = 0,
        height: Double = 0.0,
        weight: Double = 0.0,
        gender: UserGender? = null,
        firebaseToken: String,
        caretakerPhoneNumber: String = ""
    ) {
        databaseInstance.createNewUser(
            phoneNumber,
            name,
            age,
            height,
            weight,
            gender,
            firebaseToken,
            caretakerPhoneNumber
        )
        if (caretakerPhoneNumber.isNotEmpty()) {
            associateUserToCaretaker(phoneNumber, caretakerPhoneNumber)
        }
        Log.d(TAG, "addNewUser")
    }

    private fun associateUserToCaretaker(userPhoneNumber: String, caretakerPhoneNumber: String) {
        databaseInstance.addUserToCaretaker(userPhoneNumber, caretakerPhoneNumber)
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
        val updateUser = {
            databaseInstance.updateUser(
                phoneNumber,
                name,
                age,
                height,
                weight,
                gender,
                firebaseToken,
                caretakerPhoneNumber
            )
        }
        if (caretakerPhoneNumber != null) {
            removePreviousCaretakerIfExists(phoneNumber, updateUser)
            if (caretakerPhoneNumber.isNotEmpty()) {
                associateUserToCaretaker(phoneNumber, caretakerPhoneNumber)
            }
        } else {
            updateUser()
        }
        Log.i(TAG, "updateUser")
    }

    private fun removePreviousCaretakerIfExists(userPhoneNumber: String, updateUser: () -> Unit) {
        databaseInstance.readUser(userPhoneNumber) { user ->
            if (user != null) {
                val oldCaretaker = user.caretaker
                if (oldCaretaker.isNotEmpty()) {
                    dissociateUserFromCaretaker(userPhoneNumber, oldCaretaker)
                }
            }
            updateUser()
        }
    }

    private fun dissociateUserFromCaretaker(userPhoneNumber: String, caretakerPhoneNumber: String) {
        databaseInstance.removeUserFromCaretaker(userPhoneNumber, caretakerPhoneNumber)
    }

    fun getPhoneNumbersOfCaretakerUsers(phoneNumber: String, callback: (Set<String>?) -> Unit) {
        databaseInstance.readCaretaker(phoneNumber) {
            callback(it?.users?.keys ?: setOf())
        }
        Log.i(TAG, "getPhoneNumbersOfCaretakerUsers")
    }

    fun getUser(phoneNumber: String, callback: (User?) -> Unit) {
        databaseInstance.readUser(phoneNumber, callback)
    }
}