package hu.bme.aut.weathercomposearchdemo.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    companion object {
        @Volatile
        private var Instance: CityDatabase? = null

        fun getDatabase(context: Context): CityDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CityDatabase::class.java, "city_database")
                    // Setting this option in your app's database builder means that Room
                    // permanently deletes all data from the tables in your database when it
                    // attempts to perform a migration with no defined migration path.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}