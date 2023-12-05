package hu.ait.sensorcomposedemo.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class SensorLiveData(private val context: Context) : LiveData<SensorEvent>(),
    SensorEventListener {

    private val sensorManager: SensorManager
    private val acceleroSensor: Sensor

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onActive() {
        super.onActive()

        sensorManager.registerListener(
            this,
            acceleroSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

    }

    override fun onInactive() {
        super.onInactive()

        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        value = event
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}