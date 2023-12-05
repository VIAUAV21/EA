package hu.ait.sensorcomposedemo.ui.screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.ait.sensorcomposedemo.sensor.SensorLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SensorViewModel(application: Application) : ViewModel() {

    private val _sensorFlow = MutableStateFlow<FloatArray?>(null)
    val sensorFlow = _sensorFlow.asStateFlow()

    private val sensorLiveData = SensorLiveData(application)

    init {
        sensorLiveData.observeForever {
            viewModelScope.launch {
                _sensorFlow.emit(it.values.clone())
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SensorViewModel(application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])!!)
            }
        }
    }
}