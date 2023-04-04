package hu.bme.aut.weathercomposearchdemo.ui.screen.citylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    cityListViewModel: CityListViewModel = viewModel(factory = CityListViewModel.factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var showAddDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val cityList by cityListViewModel.getAllCities().collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("City list")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =
                    MaterialTheme.colorScheme.secondaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        // delete all
                    }) {
                        Icon(Icons.Filled.Delete, null)
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add city",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            LazyColumn() {
                items(cityList) {
                    CityCard(it.cityName,
                        removeItem = {
                            cityListViewModel.deleteCity(it)
                        },
                        itemClick = { cityName -> navController.navigate("weather/$cityName") }
                    )
                }
            }
            if (showAddDialog) {
                AddNewCityForm(
                    addNewCity = { cityName ->
                        coroutineScope.launch {
                            cityListViewModel.addCity(cityName)
                        }
                    },
                    dialogDismiss = { showAddDialog = false })
            }
        }
    }
}


@Composable
fun AddNewCityForm(
    addNewCity: (String) -> Unit,
    dialogDismiss: () -> Unit
) {
    Dialog(onDismissRequest = dialogDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                var cityName by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = cityName,
                    label = { Text(text = "City name") },
                    onValueChange = {
                        cityName = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(onClick = {
                    addNewCity(cityName)
                    dialogDismiss()
                }) {
                    Text(text = "Add City")
                }
            }
        }
    }
}

@Composable
fun CityCard(
    cityName: String,
    removeItem: () -> Unit,
    itemClick: (String) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp).clickable {
            itemClick(cityName)
        }
    ) {
        Row(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                modifier = Modifier
                    .weight(1f),
                text = cityName
            )

            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",

                modifier = Modifier.clickable {
                    removeItem()
                },
                tint = Color.Red
            )

        }
    }
}