package com.example.mydaylogger.location

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import java.lang.Exception

class GeofencingService(override var context: Context): LocationService {
    private val geofencingClient = LocationServices.getGeofencingClient(context)
    private val geofencingPendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
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
    }

    @SuppressLint("MissingPermission")
    fun addGeofences(onSuccessListener: () -> Unit, onFailureListener: (Exception) -> Unit) {
        if (hasLocationPermissions()) {
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
    }

    fun removeGeofence(id: String) {
        geofences.removeIf { geofence -> geofence.requestId == id }
    }
}