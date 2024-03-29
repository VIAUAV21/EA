package hu.bme.aut.architecturecomponentsdemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class SensorLifeCycleObserver(private val context: Context) : LifecycleObserver, SensorEventListener{
    private val sensorManager: SensorManager
    private val accSensor: Sensor

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startSensorMonitoring() {
        sensorManager.registerListener(
            this,
            accSensor,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopSensorMonitoring() {
        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(sensorEvent: SensorEvent?) {

        Log.d("TAG_SENSOR",
            "X: ${sensorEvent!!.values[0]} Y: ${sensorEvent!!.values[1]} Z: ${sensorEvent!!.values[2]}")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}