package hu.bme.aut.weathercomposearchdemo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * from cities ORDER BY cityName ASC")
    fun getAllCities(): Flow<List<City>>

    @Query("SELECT * from cities WHERE id = :id")
    fun getCity(id: Int): Flow<City>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Update
    suspend fun update(city: City)

    @Delete
    suspend fun delete(city: City)
}