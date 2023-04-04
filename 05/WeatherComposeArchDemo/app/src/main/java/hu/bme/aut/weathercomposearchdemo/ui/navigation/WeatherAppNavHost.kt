package hu.bme.aut.weathercomposearchdemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.weathercomposearchdemo.ui.screen.citylist.CityListScreen
import hu.bme.aut.weathercomposearchdemo.ui.screen.weather.WeatherScreen


@Composable
fun WeatherAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "citylist"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("citylist") {CityListScreen(navController = navController)}

        composable("weather/{cityname}",
            arguments = listOf(
                navArgument("cityname"){type = NavType.StringType}
            )
        ) {
            val cityName = it.arguments?.getString("cityname")
            cityName?.let {
                WeatherScreen()
            }
        }
    }
}
