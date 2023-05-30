package hu.ait.sensorcomposedemo.ui.screen

import android.hardware.SensorEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun SensorScreen(
    sensorViewModel: SensorViewModel = viewModel(factory = SensorViewModel.factory)
) {
    val sensorState by sensorViewModel.sensorFlow.collectAsState()

    Column() {
        Text(text = "Sensor value: ${getSensorTextValue(sensorState)}")
    }
}

fun getSensorTextValue(values: FloatArray?) : String {
    if (values != null) {
        val accX = values[0].toDouble()
        val accY = values[1].toDouble()
        val accZ = values[2].toDouble()
        return """
                X: $accX
                Y: $accY
                Z: $accZ
            """.trimIndent()
    }
    else {
        return ""
    }
}