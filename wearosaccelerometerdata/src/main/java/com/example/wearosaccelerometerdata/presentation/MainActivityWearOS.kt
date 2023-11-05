/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.wearosaccelerometerdata.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


private const val TAG = "MainActivityWearOS"

class MainActivityWearOS : ComponentActivity() {

    private lateinit var sensorHandler: SensorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            val serviceIntent = Intent(this, SensorService::class.java)
//            ContextCompat.startForegroundService(this, serviceIntent)
            sensorHandler = SensorHandler(this){xAxis, yAxis, zAxis ->
                Log.d(TAG,"X: $xAxis, Y: $yAxis, Z: $zAxis")

            val serviceIntent = Intent(this, SensorDataSenderService::class.java)
            startService(serviceIntent)
            }
        }
    }
}
