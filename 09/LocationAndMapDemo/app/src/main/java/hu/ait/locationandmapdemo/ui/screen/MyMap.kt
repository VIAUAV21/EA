package hu.ait.locationandmapdemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MyMap() {
    var cameraState = rememberCameraPositionState {
        CameraPosition.fromLatLngZoom(LatLng(47.0, 19.0), 10f)
    }
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
                mapType = MapType.SATELLITE,
                isTrafficEnabled = true,
                //mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.mapstyle)
            )
        )
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        uiSettings = uiSettings,
        properties = properties
    ) {
    }
}