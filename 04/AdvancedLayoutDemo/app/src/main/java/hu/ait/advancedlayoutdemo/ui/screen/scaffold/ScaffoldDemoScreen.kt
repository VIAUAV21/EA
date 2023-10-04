package hu.ait.advancedlayoutdemo.ui.screen.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldDemoScreen() {

    var text by remember { mutableStateOf("Hello, World!") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Top App Bar Demo") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {},
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = {
            innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Content")

                Spacer(modifier = Modifier.height(16.dp))
                BasicTextField(
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    },
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black)
                )
            }
        }
    )


}