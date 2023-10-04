package hu.ait.advancedlayoutdemo.ui.screen.drawer.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.ait.advancedlayoutdemo.ui.screen.drawer.AboutScreen
import hu.ait.advancedlayoutdemo.ui.screen.drawer.HomeScreen
import hu.ait.advancedlayoutdemo.ui.screen.drawer.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    drawerState: DrawerState,
    startDestination: String = MainNavigation.DrawerScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainNavigation.DrawerScreen.route) {
            HomeScreen(drawerState)
        }
        composable(MainNavigation.AboutScreen.route) {
            AboutScreen()
        }
        composable(MainNavigation.SettingsScreen.route) {
            SettingsScreen()
        }
    }
}
