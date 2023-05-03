package hu.bme.aut.firebasedemo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.firebasedemo.screen.LoginScreen
import hu.bme.aut.firebasedemo.screen.MainScreen
import hu.bme.aut.firebasedemo.screen.WritePostScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {navController.navigate(Screen.Main.route)})
        }
        composable(Screen.Main.route) {
            MainScreen(onWriteNewPostClick = {
                navController.navigate(
                    Screen.WritePost.route
                )
            })
        }
        composable(Screen.WritePost.route) {
            WritePostScreen(
                onWritePostSuccess = {
                    navController.popBackStack(Screen.Main.route, false)
                }
            )
        }
    }
}