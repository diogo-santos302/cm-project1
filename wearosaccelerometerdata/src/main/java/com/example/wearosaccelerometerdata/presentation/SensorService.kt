package com.example.wearosaccelerometerdata.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

//import com.example.wearosaccelerometerdata.presentation.AccelerometerDataView

class SensorService : Service() {

    private lateinit var googleApiClient: GoogleApiClient

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor ? = null

    private var xValue: Float = 0f
    private var yValue: Float = 0f
    private var zValue: Float = 0f

    private val accelerometerListener = object : SensorEventListener{
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            TODO("Not yet implemented")
        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val xValue = event.values[0]
                val yValue = event.values[1]
                val zValue = event.values[2]

                Log.d("AccelerometerData", "X: $xValue, Y:$yValue, Z:$zValue")

                //send the data to the handheld
                sendDataToHandheld(xValue, yValue, zValue)
            }
        }
    }

    private fun sendDataToHandheld(x: Float, y: Float, z: Float) {
        val dataMap = PutDataMapRequest.create("/accelerometer-data")
        dataMap.dataMap.putFloat("x", x)
        dataMap.dataMap.putFloat("y", y)
        dataMap.dataMap.putFloat("z", z)
        val request = dataMap.asPutDataRequest()
        Wearable.DataApi.putDataItem(googleApiClient, request)
    }


    override fun onCreate() {
        super.onCreate()

        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Wearable.API)
            .build()
        googleApiClient.connect()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Register the listener when the service starts
        accelerometer?.let {
            sensorManager.registerListener(
                accelerometerListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        // Start the service in the foreground
        startForeground(1, createNotification())

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(accelerometerListener)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        // Create a notification for the foreground service
        // You can customize the notification as needed
        val notificationChannelId = "SensorChannel"
        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Sensor Service")
            .setContentText("Collecting sensor data")
            //.setSmallIcon(R.drawable.ic_notification_icon)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(notificationChannelId, "Sensor Service", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        return notificationBuilder
    }


}