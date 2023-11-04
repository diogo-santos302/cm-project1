package com.example.mydaylogger.notifications

import android.util.Log
import com.example.mydaylogger.location.Geofence
import com.google.firebase.Firebase
import com.google.firebase.functions.HttpsCallableReference
import com.google.firebase.functions.functions
import com.google.gson.Gson

private const val TAG = "NotificationManager"
private const val SEND_NOTIFICATION_CALLABLE = "sendNotification"
private const val SEND_DATA_MESSAGE_CALLABLE = "sendDataMessage"

class NotificationManager {
    private val functions = Firebase.functions

    init {
        functions.useEmulator("10.0.2.2", 5001)
    }

    fun sendNotification(destinationFcmToken: String, title: String, body: String) {
        val messageCallable = functions.getHttpsCallable(SEND_NOTIFICATION_CALLABLE)
        val notification = hashMapOf(
            "token" to destinationFcmToken,
            "title" to title,
            "body" to body
        )
        messageCallable.call(notification)
            .continueWith { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Successfully sent notification")
                } else {
                    val exception = task.exception
                    Log.e(TAG, "Error sending notification", exception)
                }
            }
    }

    fun sendDataMessage(destinationFcmToken: String, data: Geofence) {
        val messageCallable = functions.getHttpsCallable(SEND_DATA_MESSAGE_CALLABLE)
        val stringData = hashMapOf(
            "latitude" to data.latitude.toString(),
            "longitude" to data.longitude.toString(),
            "radiusInMetres" to data.radiusInMetres.toString()
        )
        val gson = Gson()
        val dataJson = gson.toJson(stringData)
        val message = hashMapOf(
            "token" to destinationFcmToken,
            "data" to dataJson,
        )
        messageCallable.call(message)
            .continueWith { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Successfully sent notification")
                } else {
                    val exception = task.exception
                    Log.e(TAG, "Error sending notification", exception)
                }
            }
    }
}