package hu.bme.aut.composecontainers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {MainTopBar(title = "Title")},
        bottomBar = { BottomBar()},
        floatingActionButton = { MainFloatingActionButton(snackbarHostState)},
        snackbarHost = { SnackbarHost(
            hostState = snackbarHostState) },
    ) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {  }
    }
}

@Composable
fun MainFloatingActionButton(snackbarHostState: SnackbarHostState) {
    val coroutineScope = rememberCoroutineScope()

    FloatingActionButton(
        onClick = {
            coroutineScope.launch {
                val snackbarResult = snackbarHostState.showSnackbar(
                    duration = SnackbarDuration.Short,
                    message = "Item created",
                    actionLabel = "Undo"
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> { }
                    SnackbarResult.ActionPerformed -> { }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
            tint = androidx.compose.ui.graphics.Color.White,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String){
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(title)},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor =
            MaterialTheme.colorScheme.secondaryContainer
        ),
        navigationIcon = {
            IconButton(
                onClick = {  }
                    ) {
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Drawer Icon")
                    }
                },
        actions = {
            IconButton(
                onClick = { expanded = !expanded}
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false}) {
                DropdownMenuItem(onClick = { },
                    text = { Text(text = "demo") })
                DropdownMenuItem(onClick = { },
                    text = { Text(text = "Settings") })
            }
        }
    )
}

@Composable
fun BottomBar() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        NavigationBarItem(
            selected = true,
            onClick = {  },
            label = {
                Text(
                    text = "Home",
                    fontWeight = FontWeight.SemiBold,
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = true,
            onClick = {  },
            label = {
                Text(
                    text = "Add",
                    fontWeight = FontWeight.SemiBold,
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.AddCircle,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = true,
            onClick = {  },
            label = {
                Text(
                    text = "Profile",
                    fontWeight = FontWeight.SemiBold,
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = null
                )
            }
        )
    }
}