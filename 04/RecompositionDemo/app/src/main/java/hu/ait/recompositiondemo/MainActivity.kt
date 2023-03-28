package hu.ait.recompositiondemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.ait.recompositiondemo.ui.theme.RecompositionDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecompositionDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Counter("Android")
                }
            }
        }
    }
}

@Composable
fun Counter(name: String) {
    var clickCount by remember{mutableStateOf(1)}

    Column() {
        Button(onClick = {
            clickCount++
        }) {
            Log.d("TAG_COMPOSE","recompose occured in button")
            Text(text = "Press me $clickCount")
        }
        Log.d("TAG_COMPOSE","recompose occured outside")
        Text(text = "Hello $clickCount!")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RecompositionDemoTheme {
        Counter("Android")
    }
}