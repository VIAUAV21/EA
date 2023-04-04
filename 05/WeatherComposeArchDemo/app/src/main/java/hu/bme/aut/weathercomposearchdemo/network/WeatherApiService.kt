package hu.bme.aut.weathercomposearchdemo.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

// http://api.openweathermap.org/data/2.5/weather?q=Budapest&units=metric&appid=f3d694bc3e1d44c1ed5a97bd1120e8fe

private const val BASE_URL =
    "https://api.openweathermap.org/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

interface WeatherApiService {
    @GET("/data/2.5/weather")
    suspend fun getWeatherData(@Query("q") cityName: String,
                       @Query("units") units: String,
                       @Query("appid") appId: String): WeatherResult
}