package com.example.mydaylogger.app.notifications

import android.util.Log
import com.example.mydaylogger.app.firebase.MyFirebaseFunctions
import com.example.mydaylogger.app.location.Geofence
import com.google.firebase.functions.HttpsCallableReference
import com.google.gson.Gson

private const val TAG = "NotificationManager"
private const val SEND_NOTIFICATION_CALLABLE = "sendNotification"
private const val SEND_DATA_MESSAGE_CALLABLE = "sendDataMessage"

class NotificationManager {
    private val firebaseFunctions = MyFirebaseFunctions()
    private lateinit var messageCallable: HttpsCallableReference
    private var message = hashMapOf<String, Any>()

    fun sendNotification(destinationFcmToken: String, title: String, body: String) {
        messageCallable = firebaseFunctions.getHttpsCallable(SEND_NOTIFICATION_CALLABLE)
        message = hashMapOf(
            "token" to destinationFcmToken,
            "title" to title,
            "body" to body
        )
        sendMessage()
    }

    fun sendDataMessage(destinationFcmToken: String, data: Geofence) {
        messageCallable = firebaseFunctions.getHttpsCallable(SEND_DATA_MESSAGE_CALLABLE)
        val geofenceData = GeofenceData(
            data.latitude.toString(),
            data.longitude.toString(),
            data.radiusInMetres.toString()
        )
        val dataJson = Gson().toJson(geofenceData)
        message = hashMapOf(
            "token" to destinationFcmToken,
            "data" to dataJson,
        )
        sendMessage()
    }

    private fun sendMessage() {
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