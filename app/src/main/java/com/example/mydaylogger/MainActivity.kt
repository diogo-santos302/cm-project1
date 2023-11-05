package com.example.mydaylogger

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mydaylogger.location.GeofencingService
import com.example.mydaylogger.ui.theme.MyDayLoggerTheme
const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val geofencingService = GeofencingService(this)
//        val locationPermissionRequest = registerForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissions ->
//            when {
////                permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {
////                    Log.i(TAG, "Background location access granted.")
////                    addGeofence(geofencingService)
////                }
//                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
//                    Log.i(TAG, "Precise location access granted.")
//                    addGeofence(geofencingService)
//                }
//                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
//                    Log.i(TAG, "Only approximate location access granted.")
//                } else -> {
//                    Log.i(TAG, "No location access granted.")
//                }
//            }
//        }
//        when (PackageManager.PERMISSION_GRANTED) {
//            ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) -> {
//                // You can use the API that requires the permission.
//                Log.i(TAG, "You can use the API that requires the permission.")
//                addGeofence(geofencingService)
//            }
//            //            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
//            //                // In an educational UI, explain to the user why your app requires this
//            //                // permission for a specific feature to behave as expected, and what
//            //                // features are disabled if it's declined. In this UI, include a
//            //                // "cancel" or "no thanks" button that lets the user continue
//            //                // using your app without granting the permission.
//            //                showInContextUI()
//            //            }
//            else -> {
//                // You can directly ask for the permission.
//                // The registered ActivityResultCallback gets the result of this request.
//                locationPermissionRequest.launch(
//                    arrayOf(
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    )
//                )
//            }
//        }

        val intentFilter =  IntentFilter("android.intent.action.ACTION_BOOT_COMPLETED")
        registerReceiver(BootReceiver(), intentFilter)

        val serviceIntent = Intent(this, SensorDataListenerService::class.java)
        startService(serviceIntent)

            setContent {
            MyDayLoggerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //MainScreen()
                    MainScreen(this)

                }
            }

        }
    }

    private fun addGeofence(geofencingService: GeofencingService) {
        geofencingService.addGeofence("1", 40.0, 80.0)
        geofencingService.addGeofences(
            onSuccessListener = { Log.i(TAG, "Success adding geofence") },
            onFailureListener = { exception ->
                exception.message?.let { Log.e(TAG, it) }
                Log.d(TAG, "Error in adding")
            }
        )
    }

}


//@Composable
//fun MyDataListenerScreen() {
//    val context = LocalContext.current
//
//    Button(onClick = {
//        context.startService(Intent(context, MyDataListener::class.java))
//    }) {
//        Text("Start Listening")
//    }
//
//    Button(onClick = {
//        context.stopService(Intent(context, MyDataListener::class.java))
//    }) {
//        Text("Stop Listening")
//    }
//}
