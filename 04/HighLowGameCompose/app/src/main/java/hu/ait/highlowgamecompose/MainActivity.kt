package hu.ait.highlowgamecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.ait.highlowgamecompose.ui.screen.MainMenuScreen
import hu.ait.highlowgamecompose.ui.screen.GameScreen
import hu.ait.highlowgamecompose.ui.screen.help.HelpScreen
import hu.ait.highlowgamecompose.ui.theme.HighLowGameComposeTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HighLowGameComposeTheme {
                //MyApp(modifier = Modifier.fillMaxSize())
                MyAppNavHost(modifier = Modifier.fillMaxSize())
            }
        }
    }
}


@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "mainmenu"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("mainmenu") {
            MainMenuScreen(
                onNavigateToGame = { navController.navigate("game?upperBound=100") },
                navController
            )
        }
        composable("game?upperBound={upperBound}",
                arguments = listOf(navArgument("upperBound") {
                    defaultValue = 0
                    type = NavType.IntType })
            ) { GameScreen() }
        composable("help/{helptext}?userId={userId}",
            arguments = listOf(navArgument("helptext"){ type = NavType.StringType },
                navArgument("userId") {
                    defaultValue = 0
                    type = NavType.IntType })) { navBackStackEntry ->
            /* Extracting the helptext from the route */
            val text = navBackStackEntry.arguments?.getString("helptext")
            val user = navBackStackEntry.arguments?.getInt("userId")
            /* We check if argument is not null */
            text?.let {
                user?.let {
                    HelpScreen(
                        helpText = text,
                        userId = user
                    )
                }
            }
        }
        composable("about") { HelpScreen() }
    }
}