package hu.bme.aut.composeviewinteropdemo

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import hu.bme.aut.composeviewinteropdemo.databinding.LayoutDemoBinding
import hu.bme.aut.composeviewinteropdemo.ui.theme.ComposeViewInteropDemoTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeViewInteropDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ViewDemo()
                }
            }
        }
    }
}

@Composable
fun ViewDemo() {
    Column() {
        Text(text = "Hello View Demo from Compose!")

        AndroidView(
            factory = { context ->
                val binding = LayoutDemoBinding.inflate(LayoutInflater.from(context))
                binding.btnDemo.setOnClickListener {
                    binding.tvDemo.text = Date(System.currentTimeMillis()).toString()
                }

                binding.root
            },
            update = {

            }
        )
    }
}
