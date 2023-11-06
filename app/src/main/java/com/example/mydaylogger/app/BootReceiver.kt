package com.example.mydaylogger.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
private const val TAGSTART = "SensorDataListenerService"

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d(TAGSTART, "broadcast louco")

        }
    }
}