package hu.ait.roomgradedemo

import android.app.Application
import hu.ait.roomgradedemo.data.AppDatabase

class MainApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}