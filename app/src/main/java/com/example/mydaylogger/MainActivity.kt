package com.example.mydaylogger

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.mydaylogger.location.CurrentLocationService
import com.example.mydaylogger.location.Geofence
import com.example.mydaylogger.location.GeofencingService
import com.example.mydaylogger.notifications.NotificationManager
import com.example.mydaylogger.ui.theme.MyDayLoggerTheme
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var geofencingService: GeofencingService
    private lateinit var currentLocationService: CurrentLocationService

    private val notificationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(TAG, "FCM SDK (and your app) can post notifications.")
        } else {
            Log.d(TAG, "Your app will not show notifications.")
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {
                Log.d(TAG, "Background location access granted.")
                addGeofence(geofencingService)
                currentLocationService.getCurrentLocationOrNull { location ->
                    Log.i(TAG, "${location?.latitude}, ${location?.longitude}")
                }
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.d(TAG, "Precise location access granted.")
                addGeofence(geofencingService)
                currentLocationService.getCurrentLocationOrNull { location ->
                    Log.i(TAG, "${location?.latitude}, ${location?.longitude}")
                }
            } else -> {
                Log.d(TAG, "No location access granted.")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        geofencingService = GeofencingService(this)
        currentLocationService = CurrentLocationService(this)
        val notificationManager = NotificationManager()
        notificationManager.sendDataMessage(Firebase.messaging.token.result, Geofence(40.0, 80.0, 100f))
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

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "You can post notifications")
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                notificationPermissionRequest.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun askLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            askBackgroundLocationPermission()
        } else {
            askFineLocationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun askBackgroundLocationPermission() {
        val permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION
        if (ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "You can use the API that requires the permission.")
            addGeofence(geofencingService)
            currentLocationService.getCurrentLocationOrNull { location ->
                Log.i(TAG, "${location?.latitude}, ${location?.longitude}")
            }
        } else if (shouldShowRequestPermissionRationale(permission)) {
            // TODO: display an educational UI explaining to the user the features that will be enabled
            //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
            //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
            //       If the user selects "No thanks," allow the user to continue without notifications.
        } else {
            locationPermissionRequest.launch(arrayOf(permission))
        }
    }

    private fun askFineLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "You can use the API that requires the permission.")
            addGeofence(geofencingService)
            currentLocationService.getCurrentLocationOrNull { location ->
                Log.i(TAG, "${location?.latitude}, ${location?.longitude}")
            }
        } else {
            locationPermissionRequest.launch(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, permission)
            )
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
