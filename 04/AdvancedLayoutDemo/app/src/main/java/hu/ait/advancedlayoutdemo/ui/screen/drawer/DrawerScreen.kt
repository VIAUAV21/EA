package hu.ait.advancedlayoutdemo.ui.screen.drawer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import hu.ait.advancedlayoutdemo.R
import hu.ait.advancedlayoutdemo.ui.screen.drawer.navigation.DrawerMainNavHost
import hu.ait.advancedlayoutdemo.ui.screen.drawer.navigation.MainNavigation
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
                defaultPick = MainNavOption.HomeScreen
            ) { onUserPickedOption ->
                when (onUserPickedOption) {
                    MainNavOption.HomeScreen -> {
                        navController.navigate(MainNavigation.DrawerScreen.route)
                    }

                    MainNavOption.SettingsScreen -> {
                        navController.navigate(MainNavigation.SettingsScreen.route)
                    }

                    MainNavOption.AboutScreen -> {
                        navController.navigate(MainNavigation.AboutScreen.route)
                    }

                    MainNavOption.DemoScreen -> {

                    }
                }
            }
        }
    ) {
        // NavHost...
        DrawerMainNavHost(navController = navController, drawerState = drawerState)
    }
}



enum class MainNavOption {
    HomeScreen,
    AboutScreen,
    SettingsScreen,
    DemoScreen,
}

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItemInfo(
            MainNavOption.HomeScreen,
            R.string.drawer_home,
            R.drawable.ic_info,
            R.string.drawer_home_description
        ),
        AppDrawerItemInfo(
            MainNavOption.SettingsScreen,
            R.string.drawer_settings,
            R.drawable.ic_info,
            R.string.drawer_settings_description
        ),
        AppDrawerItemInfo(
            MainNavOption.AboutScreen,
            R.string.drawer_about,
            R.drawable.ic_info,
            R.string.drawer_info_description
        ),
        AppDrawerItemInfo(
            MainNavOption.DemoScreen,
            R.string.drawer_demo,
            R.drawable.ic_info,
            R.string.drawer_info_description
        )
    )
}



data class AppDrawerItemInfo<T>(
    val drawerOption: T,
    @StringRes val title: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val descriptionId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Enum<T>> AppDrawerContent(
    drawerState: DrawerState,
    menuItems: List<AppDrawerItemInfo<T>>,
    defaultPick: T,
    onClick: (T) -> Unit
) {
    var currentPick by remember { mutableStateOf(defaultPick) }
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Column {
                    Text(text = "Title")
                }
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()  //fill the max height
                        .height(5.dp)
                )
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(menuItems) { item ->
                        AppDrawerItem(item = item) { navOption ->
                            if (currentPick == navOption) {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                                return@AppDrawerItem
                            }

                            currentPick = navOption
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            onClick(navOption)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDrawerItem(item: AppDrawerItemInfo<T>, onClick: (options: T) -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onClick(item.drawerOption) },
        shape = RoundedCornerShape(50),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = item.drawableId),
                contentDescription = stringResource(id = item.descriptionId),
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = item.title),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}