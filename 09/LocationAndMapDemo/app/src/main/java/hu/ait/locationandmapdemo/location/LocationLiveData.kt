package hu.ait.locationandmapdemo.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*
import kotlin.jvm.Throws

class LocationLiveData(context: Context) : LiveData<Location>() {

    private var fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    override fun onInactive() {
        super.onInactive()
        stopLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
    }

    @SuppressLint("MissingPermission")
    public fun startLocationUpdates() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                value = location
            }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    @Throws(SecurityException::class)
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            value = locationResult.lastLocation!!
        }
    }

    companion object {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).apply {
            setMinUpdateDistanceMeters(5f)
            setGranularity(Granularity.GRANULARITY_FINE)
            //setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()
    }
}