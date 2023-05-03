package hu.bme.aut.daggerbasicdemo

import android.app.Application

class MainApplication : Application() {
    lateinit var injector: InfoComponent

    override fun onCreate() {
        super.onCreate()
        injector = DaggerInfoComponent.builder().vehicleModule(
            VehicleModule("Tesla")).build()
    }
}