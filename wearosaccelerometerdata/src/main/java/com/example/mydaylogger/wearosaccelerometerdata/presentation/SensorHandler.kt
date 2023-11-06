package com.example.mydaylogger.wearosaccelerometerdata.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log




private const val TAG = "SensorHandler"

class SensorHandler(
    private val context: Context,
    private val sensorCallback: (Float, Float, Float) -> Unit) :
    SensorEventListener {

    private lateinit var sensorManager: SensorManager

    init {
        setUpSensorStuff()
    }

    // Register the listener
    private fun setUpSensorStuff() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it, //the sensor
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // Retrieve the information
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) { //if it is this kind of sensor we want to retrieve its values
            val xAxis = event.values[0]
            val yAxis = event.values[1]
            val zAxis = event.values[2]

            sensorCallback(xAxis, yAxis, zAxis)

            Log.d(TAG, "X: $xAxis, Y: $yAxis, Z: $zAxis")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}