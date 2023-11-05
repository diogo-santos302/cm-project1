package com.example.mydaylogger

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

private const val TAG1 = "SensorDataListenerService"
private const val X_AXIS_FLOAT_KEY = "com.example.key.xaxisfloat"
private const val Y_AXIS_FLOAT_KEY = "com.example.key.yaxisfloat"
private const val Z_AXIS_FLOAT_KEY = "com.example.key.zaxisfloat"
class SensorDataListenerService : Service(), DataClient.OnDataChangedListener {

    private lateinit var dataClient: DataClient

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
        dataEvents.forEach { event ->
            if (event.type == DataEvent.TYPE_CHANGED) {
                try {
                    event.dataItem.also { item ->
                        if (item.uri.path?.compareTo("/numberranchopt") == 0) {
                            DataMapItem.fromDataItem(item).dataMap.apply {
                                Log.d(TAG1, "louco: x: ${getFloat(X_AXIS_FLOAT_KEY)} y ${getFloat(Y_AXIS_FLOAT_KEY)} z ${getFloat(Z_AXIS_FLOAT_KEY)}")


                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG1, "Error while processing data item: $e")
                }
            }
        }

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
