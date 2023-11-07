package com.example.mydaylogger.app.notifications

import android.content.Context
import android.util.Log
import com.example.mydaylogger.app.data.DatabaseManager
import com.example.mydaylogger.app.location.CurrentLocationService

private const val TAG = "Alarm"

class Alarm(private val context: Context) {
    fun send(userPhoneNumber: String?) {
        if (userPhoneNumber == null) {
            return
        }
        val title = "MyDayLogger Alarm"
        val databaseManager = DatabaseManager()
        databaseManager.getCaretakerAssociatedToUser(userPhoneNumber) { caretaker ->
            if (caretaker != null) {
                Log.d(TAG, "caretaker not null")
                val caretakerFirebaseToken = caretaker.firebaseToken
                val userLocation = CurrentLocationService(context)
                userLocation.getCurrentLocationOrNull {  location ->
                    Log.d(TAG, "HERE")
                    if (location != null) {
                        val body = "The user with phone number $userPhoneNumber is having an attack!" +
                                "\nTheir current location is ${location.latitude}, ${location.longitude}"
                        Log.d(TAG, "${location.latitude}, ${location.longitude}")
                        val notificationManager = NotificationManager()
                        notificationManager.sendNotification(caretakerFirebaseToken, title, body)
                    }
                }
            }
        }
    }
}