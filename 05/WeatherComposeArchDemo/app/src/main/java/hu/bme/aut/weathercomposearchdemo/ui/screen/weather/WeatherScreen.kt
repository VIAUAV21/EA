package hu.bme.aut.weathercomposearchdemo.ui.screen.weather

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import hu.bme.aut.weathercomposearchdemo.network.WeatherResult
import hu.bme.aut.weathercomposearchdemo.ui.screen.citylist.CityListViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    when (weatherViewModel.weatherUiState) {
        is WeatherUiState.Loading -> LoadingScreen(modifier)
        is WeatherUiState.Success -> ResultScreen((weatherViewModel.weatherUiState as WeatherUiState.Success).weahterResult, modifier)
        is WeatherUiState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.size(200.dp),
            text = "Loading..."
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text("Error...")
    }
}

@Composable
fun ResultScreen(weatherResult: WeatherResult, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(getWeatherText(weatherResult))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://openweathermap.org/img/w/${
                    weatherResult.weather[0].icon
                }.png")
                .crossfade(true)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )
    }
}

fun getWeatherText(weatherResult: WeatherResult) : String {
    val sdf = SimpleDateFormat("h:mm a z", Locale.getDefault())
    val sunriseDate = Date((weatherResult?.sys?.sunrise?.toLong())!! * 1000)
    val sunriseTime = sdf.format(sunriseDate)
    val sunsetDate = Date(weatherResult.sys.sunset?.toLong()!! * 1000)
    val sunsetTime = sdf.format(sunsetDate)

    val result =  """
        ${weatherResult?.weather?.get(0)?.main}
        ${weatherResult?.weather?.get(0)?.description}
        Temperature (celsius): ${weatherResult?.main?.temp?.toFloat().toString()}
        Sunrise: $sunriseTime
        Sunset: $sunsetTime
    """.trimIndent()
    return result



}