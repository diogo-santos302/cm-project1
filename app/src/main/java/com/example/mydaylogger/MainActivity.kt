package com.example.mydaylogger

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.mydaylogger.location.CurrentLocationService
import com.example.mydaylogger.location.GeofencingService
import com.example.mydaylogger.ui.theme.MyDayLoggerTheme
import com.example.mydaylogger.ui.ProfileScreen

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var geofencingService: GeofencingService
    private lateinit var currentLocationService: CurrentLocationService

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(TAG, "FCM SDK (and your app) can post notifications.")
        } else {
            // TODO: Inform user that that your app will not show notifications.
            Log.d(TAG, "Your app will not show notifications.")
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
//                permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {
//                    Log.i(TAG, "Background location access granted.")
//                    addGeofence(geofencingService)
//                }
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.i(TAG, "Precise location access granted.")
                addGeofence(geofencingService)
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.i(TAG, "Only approximate location access granted.")
            } else -> {
                Log.i(TAG, "No location access granted.")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        geofencingService = GeofencingService(this)
        currentLocationService = CurrentLocationService(this)
        askNotificationPermission()
        askLocationPermission()

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
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun askLocationPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                // You can use the API that requires the permission.
                Log.i(TAG, "You can use the API that requires the permission.")
                addGeofence(geofencingService)
                currentLocationService.getCurrentLocationOrNull { location ->
                    Log.i(TAG, "${location?.latitude}, ${location?.longitude}")
                }
            }
            //            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
            //                // In an educational UI, explain to the user why your app requires this
            //                // permission for a specific feature to behave as expected, and what
            //                // features are disabled if it's declined. In this UI, include a
            //                // "cancel" or "no thanks" button that lets the user continue
            //                // using your app without granting the permission.
            //                showInContextUI()
            //            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
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
