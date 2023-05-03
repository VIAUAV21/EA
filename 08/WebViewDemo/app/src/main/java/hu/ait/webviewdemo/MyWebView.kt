package hu.ait.webviewdemo

import androidx.compose.runtime.Composable
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState

val initialUrl = "https://www.aut.bme.hu/"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWebView() {
    val state = rememberWebViewState(url = initialUrl)
    val navigator = rememberWebViewNavigator()
    var textFieldValue by remember(state.lastLoadedUrl) {
        mutableStateOf(state.lastLoadedUrl)
    }

    Column {
        TopAppBar(
            title = { Text(text = "WebView Sample") },
            navigationIcon = {
                if (navigator.canGoBack) {
                    IconButton(onClick = { navigator.navigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }
        )

        Row {
            Box(modifier = Modifier.weight(1f)) {
                if (state.errorsForCurrentRequest.isNotEmpty()) {
                    Image(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Error",
                        colorFilter = ColorFilter.tint(Color.Red),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(8.dp)
                    )
                }

                OutlinedTextField(
                    value = textFieldValue ?: "",
                    onValueChange = { textFieldValue = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    textFieldValue?.let {
                        navigator.loadUrl(it)
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text("Go")
            }
        }

        val loadingState = state.loadingState
        if (loadingState is LoadingState.Loading) {
            LinearProgressIndicator(
                progress = loadingState.progress,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // A custom WebViewClient and WebChromeClient can be provided via subclassing
        val webClient = remember {
            object : AccompanistWebViewClient() {
                override fun onPageStarted(
                    view: WebView,
                    url: String?,
                    favicon: Bitmap?
                ) {
                    super.onPageStarted(view, url, favicon)
                    Log.d("Accompanist WebView", "Page started loading for $url")
                }
            }
        }

        WebView(
            state = state,
            modifier = Modifier
                .weight(1f),
            navigator = navigator,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
            client = webClient
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWebViewSimple() {
    val state = rememberWebViewState(url = initialUrl)
    val navigator = rememberWebViewNavigator()
    Column {
        val webClient = remember {
            object : AccompanistWebViewClient(){}
        }
        WebView(
            state = state,
            modifier = Modifier
                .weight(1f),
            navigator = navigator,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
            client = webClient
        )
    }
}

