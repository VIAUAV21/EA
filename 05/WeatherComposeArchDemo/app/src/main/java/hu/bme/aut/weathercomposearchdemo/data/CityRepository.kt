package hu.bme.aut.weathercomposearchdemo.data

import kotlinx.coroutines.flow.Flow

class CityRepository(private val cityDao: CityDao)  {
    fun getAllCitiesStream(): Flow<List<City>> = cityDao.getAllCities()

    fun getCityStream(id: Int): Flow<City?> = cityDao.getCity(id)

    suspend fun insertCity(item: City) = cityDao.insert(item)

    suspend fun deleteCity(item: City) = cityDao.delete(item)

    suspend fun updateCity(item: City) = cityDao.update(item)
}