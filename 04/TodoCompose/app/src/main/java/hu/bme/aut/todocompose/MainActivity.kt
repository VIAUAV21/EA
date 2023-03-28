package hu.bme.aut.todocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.todocompose.ui.screen.todolist.TodoListScreen
import hu.bme.aut.todocompose.ui.screen.todosummary.TodoSummaryScreen
import hu.bme.aut.todocompose.ui.theme.TodoComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoAppNavHost()
                }
            }
        }
    }
}

@Composable
fun TodoAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "todolist"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("todolist") { TodoListScreen(summaryClickHandler =  {
            all, important -> navController.navigate("summary/$all/$important")
        }) }

        composable("summary/{numalltodo}/{numimportant}",
            arguments = listOf(
                navArgument("numalltodo"){type = NavType.IntType},
                navArgument("numimportant"){type = NavType.IntType}
            )
        ) {
            val numalltodo = it.arguments?.getInt("numalltodo")
            val numimportant = it.arguments?.getInt("numimportant")
            if (numalltodo != null && numimportant != null) {
                TodoSummaryScreen(numalltodo = numalltodo, numimportanttodo = numimportant)
            }
        }
    }
}