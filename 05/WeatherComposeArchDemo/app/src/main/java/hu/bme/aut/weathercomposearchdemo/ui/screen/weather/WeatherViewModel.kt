package hu.bme.aut.weathercomposearchdemo.ui.screen.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.weathercomposearchdemo.network.WeatherApi
import hu.bme.aut.weathercomposearchdemo.network.WeatherResult
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


/**
 * UI state for the weather screen
 */
sealed interface WeatherUiState {
    data class Success(val weahterResult: WeatherResult) : WeatherUiState
    object Error : WeatherUiState
    object Loading : WeatherUiState
}

class WeatherViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set

    init {
        savedStateHandle.get<String>("cityname")?.let {
            getWeather(it)
        }
    }

    private fun getWeather(cityName: String) {
        viewModelScope.launch {
            weatherUiState = try {
                val result = WeatherApi.retrofitService.getWeatherData(
                    cityName,"metric","f3d694bc3e1d44c1ed5a97bd1120e8fe"
                )
                WeatherUiState.Success(result)
            } catch (e: IOException) {
                WeatherUiState.Error
            } catch (e: HttpException) {
                WeatherUiState.Error
            }
        }
    }
}