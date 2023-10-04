package hu.ait.advancedlayoutdemo.ui.screen.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(drawerState: DrawerState) {
    Scaffold(
        topBar = {
            // to run the animation independently
            val coroutineScope = rememberCoroutineScope()
            TopAppBar(
                title = {}, // no title
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            // opens drawer
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            // internal hamburger menu
                            Icons.Rounded.Menu,
                            contentDescription = "MenuButton"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Surface {
            // padding of the scaffold is enforced to be used
            Column(modifier = Modifier.padding(paddingValues)) {
                Text("Home")
            }
        }
    }
}