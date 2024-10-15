package hu.bme.aut.mvidemonewsscreen.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun NewsScreen(
    viewModel: NewsViewModel = viewModel()) {

    val state = viewModel.state.collectAsState().value

    /*LaunchedEffect(Unit) {
        viewModel.loadNews()
    }*/

    Column {

        when(state) {
            is NewsState.Init -> {
                Button(onClick = { viewModel.loadNews() }) {
                    Text(text = "Kezdjünk")
                }
            }
            is NewsState.Loading ->
                CircularProgressIndicator()
            is NewsState.Error ->
                Text(text = "Error: ${state.error.message}")
            is NewsState.Success -> {
                Text(text = state.newsText)
            }
        }

    }

}