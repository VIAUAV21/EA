package hu.bme.aut.weathercomposearchdemo

import android.app.Application
import hu.bme.aut.weathercomposearchdemo.data.CityDatabase

class MyApplication : Application() {

    val database: CityDatabase by lazy { CityDatabase.getDatabase(this) }
}