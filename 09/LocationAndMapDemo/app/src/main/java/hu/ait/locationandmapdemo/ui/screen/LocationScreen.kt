package hu.ait.locationandmapdemo.ui.screen

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import hu.ait.locationandmapdemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.factory)
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val trackedLocations by locationViewModel.locationsFlow.collectAsState()

    var markerPosition = remember {
        listOf(LatLng(1.35, 103.87)).toMutableStateList()
    }
    var cameraState = rememberCameraPositionState {
        CameraPosition.fromLatLngZoom(LatLng(47.0, 19.0), 10f)
    }

    var locationState = locationViewModel.getLocationLiveData().observeAsState()

    val fineLocationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    var geocodeText by remember {
        mutableStateOf("")
    }

    Column {
        if (fineLocationPermissionState.status.isGranted) {
            Button(onClick = {
                locationViewModel.getLocationLiveData().startLocationUpdates()
            }) {
                Text(text = "Start location monitoring")
            }
        } else {
            Column() {
                val permissionText = if (fineLocationPermissionState.status.shouldShowRationale) {
                    "Recondiert giving permisssion"
                } else {
                    "Give permission for location"
                }
                Text(text = permissionText)
                Button(onClick = {
                    fineLocationPermissionState.launchPermissionRequest()
                }) {
                    Text(text = "Request permission")
                }
            }
        }

        Text(text = "Location: ${getLocationText(locationState.value)}"
            ,
            fontSize = 28.sp)




        Text(
            text = "Is camera moving: ${cameraState.isMoving}" +
                    "\n Latitude and Longitude: ${cameraState.position.target.latitude} " +
                    "and ${cameraState.position.target.longitude}",
            textAlign = TextAlign.Center
        )


        var uiSettings by remember {
            mutableStateOf(
                MapUiSettings(
                    zoomControlsEnabled = true,
                    zoomGesturesEnabled = true,
                    scrollGesturesEnabled = true,
                    compassEnabled = true,
                    mapToolbarEnabled = true
                )
            )
        }
        var properties by remember {
            mutableStateOf(
                MapProperties(
                    mapType = MapType.NORMAL,
                    isTrafficEnabled = true,
                    //mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle)
                )
            )
        }

        var isSatellite by remember {
            mutableStateOf(false)
        }
        Switch(
            checked = isSatellite,
            onCheckedChange = {
                isSatellite = it
                properties = properties.copy(
                    mapType = if (isSatellite) MapType.SATELLITE else MapType.NORMAL
                )
            }
        )

        Text(
            text = "$geocodeText"
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraState,
            uiSettings = uiSettings,
            onMapClick = {
                markerPosition.add(it)
                val random = Random(System.currentTimeMillis())
                val cameraPostion = CameraPosition.Builder()
                    .target(it)
                    .zoom(1f + random.nextInt(5))
                    .tilt(30f + random.nextInt(15))
                    .bearing(-45f + random.nextInt(90))
                    .build()
                //cameraState.position = cameraPostion
                coroutineScope.launch {
                    cameraState.animate(
                        CameraUpdateFactory.newCameraPosition(cameraPostion), 3000
                    )
                }
            },
            properties = properties
        ) {


            for (position in markerPosition) {
                Marker(
                    state = MarkerState(position = position),
                    title = "Marker",
                    snippet = "Marker in ${position.latitude} ${position.longitude}",
                    draggable = true,
                    onClick = {

                        val geocoder = Geocoder(context, Locale.getDefault())
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            geocoder.getFromLocation(
                                it.position.latitude,
                                it.position.longitude,
                                3,
                                object : Geocoder.GeocodeListener {
                                    override fun onGeocode(addrs: MutableList<Address>) {
                                        val addr =
                                            "${addrs[0].getAddressLine(0)}, ${
                                                addrs[0].getAddressLine(
                                                    1
                                                )
                                            }, ${addrs[0].getAddressLine(2)}"

                                        geocodeText = addr
                                    }

                                    override fun onError(errorMessage: String?) {
                                        geocodeText = errorMessage!!
                                        super.onError(errorMessage)

                                    }
                                })
                        }
                        true
                    }
                )
            }

            Polyline(
                points = trackedLocations.toMutableStateList(),
                color = androidx.compose.ui.graphics.Color.Red,
                visible = true,
                width = 30f
            )

            Polyline(
                points = listOf(
                    LatLng(44.811058, 20.4617586),
                    LatLng(44.811058, 20.4627586),
                    LatLng(44.810058, 20.4627586),
                    LatLng(44.809058, 20.4627586),
                    LatLng(44.809058, 20.4617586)
                ), color = androidx.compose.ui.graphics.Color.Red
            )

        }
    }


}


fun getLocationText(location: Location?): String {
    return """
       Lat: ${location?.latitude}
       Lng: ${location?.longitude}
       Alt: ${location?.altitude}
       Speed: ${location?.speed}
       Accuracy: ${location?.accuracy}
    """.trimIndent()
}







