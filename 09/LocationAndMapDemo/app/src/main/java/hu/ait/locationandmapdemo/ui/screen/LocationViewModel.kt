package hu.ait.locationandmapdemo.ui.screen

import android.app.Application
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import hu.ait.locationandmapdemo.location.LocationLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import java.util.*

class LocationViewModel(application: Application) : ViewModel() {

    var locationsFlow = MutableStateFlow(mutableListOf<LatLng>())

    private val locationLiveData = LocationLiveData(application)

    fun getLocationLiveData() = locationLiveData

    init {
        locationLiveData.observeForever {
            if (it != null) {
                locationsFlow.value.add(LatLng(it.latitude, it.longitude))
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LocationViewModel(application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])!!)
            }
        }
    }
}