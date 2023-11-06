package com.example.mydaylogger.app.location

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

private const val TAG = "GeofencingService"

class GeofencingService(override var context: Context): LocationService {
    private val geofencingClient = LocationServices.getGeofencingClient(context)
    private val geofencingPendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }
    private val geofences = mutableListOf<Geofence>()

    fun addGeofence(id: String, latitude: Double, longitude: Double, radiusInMeters: Float = 100f) {
        geofences.add(Geofence.Builder()
            .setRequestId(id)
            .setCircularRegion(latitude, longitude, radiusInMeters)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                    or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build())
        Log.i(TAG, "Added geofence $id " +
                "at ($latitude, $longitude) with a radius of $radiusInMeters meters")
    }

    @SuppressLint("MissingPermission")
    fun addGeofences(onSuccessListener: () -> Unit, onFailureListener: (Exception) -> Unit) {
        Log.d(TAG, "@addGeofences")
        if (geofences.isEmpty()) {
            return
        }
        if (hasLocationPermissions()) {
            Log.d(TAG, "@addGeofenses with permissions")
            geofencingClient.addGeofences(getGeofencingRequest(), geofencingPendingIntent).run {
                addOnSuccessListener { onSuccessListener() }
                addOnFailureListener { onFailureListener(it) }
            }
        }
    }

    private fun getGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER
                    or GeofencingRequest.INITIAL_TRIGGER_EXIT)
            addGeofences(geofences)
        }.build()
    }

    fun removeAllGeofences(onSuccessListener: () -> Unit, onFailureListener: (Exception) -> Unit) {
        geofencingClient.removeGeofences(geofencingPendingIntent).run {
            addOnSuccessListener { onSuccessListener() }
            addOnFailureListener { onFailureListener(it) }
        }
        Log.i(TAG, "Removed all geofences")
    }

    fun removeGeofence(
        id: String,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        geofences.removeIf { geofence -> geofence.requestId == id }
        geofencingClient.removeGeofences(listOf(id)).run {
            addOnSuccessListener { onSuccessListener() }
            addOnFailureListener { onFailureListener(it) }
        }
        Log.i(TAG, "Removed geofences")
    }
}