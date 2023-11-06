package com.example.mydaylogger.wearosaccelerometerdata.presentation

import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import java.util.concurrent.CancellationException

private const val TAG1 = "WearableSender"
private const val X_AXIS_FLOAT_KEY = "com.example.key.xaxisfloat"
private const val Y_AXIS_FLOAT_KEY = "com.example.key.yaxisfloat"
private const val Z_AXIS_FLOAT_KEY = "com.example.key.zaxisfloat"
class SensorDataSenderService : WearableListenerService() {

    private lateinit var sensorHandler: SensorHandler
    private lateinit var dataClient: DataClient

    //getting the service to run all the time
    private lateinit var wakeLock: PowerManager.WakeLock

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG1, "Criou o sender")

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SensorDataSenderService:WakeLock")



        //Initialize SensorHandler
        sensorHandler = SensorHandler(this){xAxis, yAxis, zAxis ->
            sendDataToHandheld(xAxis, yAxis, zAxis)
        }

        //Initialize data client
        dataClient = Wearable.getDataClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }


    private fun sendDataToHandheld(xAxis: Float, yAxis: Float,zAxis: Float){

        try {
            val request = PutDataMapRequest.create("/numberranchopt").apply {
                dataMap.putFloat(X_AXIS_FLOAT_KEY, xAxis)
                dataMap.putFloat(Y_AXIS_FLOAT_KEY, yAxis)
                dataMap.putFloat(Z_AXIS_FLOAT_KEY, zAxis)
            }
                .asPutDataRequest()
                .setUrgent()

            val result =  dataClient.putDataItem(request)

            Log.d(TAG1, "DataItem saved: $result")
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (exception: Exception) {
            Log.d(TAG1, "Saving DataItem failed: $exception")
        }


        Log.d(TAG1, "start sending")

        val dataMap = PutDataMapRequest.create("/sensor_data")
        dataMap.dataMap.putFloat("xAxis", xAxis)
        dataMap.dataMap.putFloat("yAxis", yAxis)
        dataMap.dataMap.putFloat("zAxis", zAxis)

        val request = dataMap.asPutDataRequest()
        request.setUrgent()

        val dataClient = Wearable.getDataClient(this)
        dataClient.putDataItem(request).addOnSuccessListener {
            Log.d(TAG1, "Successfully sent accelerometer data to handheld device")
        }.addOnFailureListener {
            Log.e(TAG1, "Failed to send accelerometer data to handheld device: $it")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorHandler.unregisterListener()

        //Release the wakelock
        if (wakeLock.isHeld) {
            wakeLock.release()
        }
    }
}