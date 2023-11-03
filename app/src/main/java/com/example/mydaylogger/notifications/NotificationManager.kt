package com.example.mydaylogger.notifications

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.functions.HttpsCallableReference
import com.google.firebase.functions.functions
import com.google.firebase.messaging.messaging

private const val TAG = "NotificationManager"
private const val SEND_NOTIFICATION_CALLABLE = "sendNotification"

class NotificationManager {
    private val functions = Firebase.functions
    private val addMessageCallable: HttpsCallableReference

    init {
        functions.useEmulator("10.0.2.2", 5001)
        addMessageCallable = functions.getHttpsCallable(SEND_NOTIFICATION_CALLABLE)
    }

    fun sendNotification(destinationFcmToken: String, title: String, body: String) {
        val notification = hashMapOf(
            "token" to destinationFcmToken,
            "title" to title,
            "body" to body
        )
        addMessageCallable.call(notification)
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