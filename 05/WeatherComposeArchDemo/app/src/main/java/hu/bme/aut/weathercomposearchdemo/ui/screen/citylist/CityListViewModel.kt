package hu.bme.aut.weathercomposearchdemo.ui.screen.citylist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.weathercomposearchdemo.MyApplication
import hu.bme.aut.weathercomposearchdemo.data.City
import hu.bme.aut.weathercomposearchdemo.data.CityDao
import hu.bme.aut.weathercomposearchdemo.data.CityDatabase
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CityListViewModel(private val cityDao: CityDao) : ViewModel() {

    fun getAllCities(): Flow<List<City>> = cityDao.getAllCities()

    suspend fun addCity(cityName: String) {
        cityDao.insert(City(cityName = cityName))
    }

    fun deleteCity(city: City) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cityDao.delete(city)
            }
        }

    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MyApplication)
                CityListViewModel(application.database.cityDao())
            }
        }
    }
}