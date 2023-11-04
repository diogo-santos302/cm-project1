package com.example.mydaylogger

import android.Manifest
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
import com.example.mydaylogger.data.DatabaseManager
import com.example.mydaylogger.firebase.MyRealtimeDatabase
import com.example.mydaylogger.location.CurrentLocationService
import com.example.mydaylogger.location.GeofencingService
import com.example.mydaylogger.notifications.NotificationManager
import com.example.mydaylogger.ui.theme.MyDayLoggerTheme
import com.example.mydaylogger.ui.ProfileScreen
import com.google.firebase.Firebase
import com.google.firebase.functions.functions
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
//        askNotificationPermission()
//        askLocationPermission()
//        val notificationManager = NotificationManager()
//        val firebaseToken = Firebase.messaging.token.result
//        Log.d(TAG, firebaseToken)
//        notificationManager.sendNotification(firebaseToken, "Hello World", "Yo")
        val databaseManager = DatabaseManager(MyRealtimeDatabase)
//        databaseManager.updateUser(phoneNumber="929292929", caretakerPhoneNumber = "909090909")
//        databaseManager.updateUser(phoneNumber="919191919", caretakerPhoneNumber = "909090909")
//        databaseManager.addNewUser(phoneNumber="929292929", name="Alice", firebaseToken = Firebase.messaging.token.result, caretakerPhoneNumber = "919191919")
//        databaseManager.getPhoneNumbersOfCaretakerUsers("909090909") {
//            Log.d(TAG, it.toString())
//        }
        databaseManager.getUser("919191919") {
            Log.d(TAG, it.toString())
        }
        setContent {
            MyDayLoggerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileScreen()
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
