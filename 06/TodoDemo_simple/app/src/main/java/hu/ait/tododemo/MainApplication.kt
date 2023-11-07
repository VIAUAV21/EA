package hu.ait.tododemo

import android.app.Application
import hu.ait.tododemo.data.AppDatabase

class MainApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}