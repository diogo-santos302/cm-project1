package com.example.mydaylogger.app

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.Wearable
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.math.abs

private const val TAG1 = "SensorDataListenerService"
private const val X_AXIS_FLOAT_KEY = "com.example.key.xaxisfloat"
private const val Y_AXIS_FLOAT_KEY = "com.example.key.yaxisfloat"
private const val Z_AXIS_FLOAT_KEY = "com.example.key.zaxisfloat"
class SensorDataListenerService : Service(), DataClient.OnDataChangedListener {

    private lateinit var dataClient: DataClient

    private val THRESHOLD = 2.0f
    private val COUNT_THRESHOLD = 50
    private var count = 0
    private var lastUpdateTime = System.currentTimeMillis()
    private val prevAcelG = floatArrayOf(0.0f, 0.0f, 0.0f)

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG1,"louco começo")
        try {
            dataClient = Wearable.getDataClient(this)
            dataClient.addListener(this)
        } catch (e: Exception) {
            when (e) {
                is FileNotFoundException -> {
                    Log.d(TAG1, "file gone")                }
                is IOException -> {
                    Log.d(TAG1, "loucura total")                }
                else -> {
                    Log.d(TAG1, "Error while initializing DataClient: $e")
                }
            }
        }
    }



    override fun onDataChanged(dataEvents: DataEventBuffer) {
        Log.d(TAG1, "louco data change");
        val currentTime = System.currentTimeMillis()
        dataEvents.forEach { event ->
            if (event.type == DataEvent.TYPE_CHANGED) {
                try {
                    event.dataItem.also { item ->
                        if (item.uri.path?.compareTo("/numberranchopt") == 0) {
                            DataMapItem.fromDataItem(item).dataMap.apply {
                                Log.d(
                                    TAG1, "louco: x: ${getFloat(X_AXIS_FLOAT_KEY)} y ${getFloat(
                                        Y_AXIS_FLOAT_KEY
                                    )} z ${getFloat(Z_AXIS_FLOAT_KEY)}")
                                val x = getFloat(X_AXIS_FLOAT_KEY)
                                val y = getFloat(Y_AXIS_FLOAT_KEY)
                                val z = getFloat(Z_AXIS_FLOAT_KEY)

                                val acelG = floatArrayOf(x, y, z)

                                if (isSuddenChange(acelG)) {
                                    count ++

                                    Log.d(
                                        TAG1, "Sudden change detected: $count" +
                                            "-------------------------------->")

                                }

                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG1, "Error while processing data item: $e")
                }
            }
        }

        if (currentTime - lastUpdateTime > 50000) {
            // Reset counter
            count = 0
            lastUpdateTime = currentTime
        }

        if (count >= COUNT_THRESHOLD){
            //Trigger seizure warning
            Log.d(TAG1, "--------------------------->Seizure detected")
            count = 0
        }
    }

    fun isSuddenChange(acelG: FloatArray): Boolean {

        for (i in 0 until 3) {
            val diff = abs(acelG[i] - prevAcelG[i])
            Log.d(TAG1, "Previous Values: x=${prevAcelG[0]}, y=${prevAcelG[1]}, z=${prevAcelG[2]}")

            if (diff >= THRESHOLD) {
                prevAcelG[i] = acelG[i]
                return true
            }
            prevAcelG[i] = acelG[i]
        }
        return false
    }


    override fun onDestroy() {
        super.onDestroy()

        // Remove o listener para eventos de itens de dados
        dataClient.removeListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }
}

//class SensorDataListenerService :  WearableListenerService() {
//    private lateinit var dataClient: DataClient
//
//    override fun onCreate() {
//        super.onCreate()
//        dataClient = Wearable.getDataClient(this)
//    }
//    override fun onDataChanged() {
//
//        // Get the data item
//        val dataItem = dataClient.getDataItem("/sensor_data").await()
//
//        // Check if the data item is for the sensor data we are interested in
//        if (dataItem.uri.path != "/sensor_data") {
//            return
//        }
//
//        // Get the sensor data from the data map
//        val dataMap = dataItem.dataMap
//        val xAxis = dataMap.getFloat("xAxis")
//        val yAxis = dataMap.getFloat("yAxis")
//        val zAxis = dataMap.getFloat("zAxis")
//
//        // Do something with the sensor data
//        Log.d("SensorDataListenerService", "Sensor data received: $xAxis, $yAxis, $zAxis")
//    }
//}
