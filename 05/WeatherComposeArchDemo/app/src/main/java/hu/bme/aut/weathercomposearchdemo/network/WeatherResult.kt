package hu.bme.aut.weathercomposearchdemo.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class WeatherResult(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)

@Serializable
data class Rain(
    val `1h`: Long
)

@Serializable
data class Clouds(
    val all: Long
)

@Serializable
data class Coord(
    val lon: Double,
    val lat: Double
)

@Serializable
data class Main(
    val temp: Double,

    @SerialName("feels_like")
    val feelsLike: Double,

    @SerialName("temp_min")
    val tempMin: Double,

    @SerialName("temp_max")
    val tempMax: Double,

    val pressure: Long,
    val humidity: Long
)

@Serializable
data class Sys(
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

@Serializable
data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Wind(
    val speed: Double,
    val deg: Long
)
