package hu.ait.advancedlayoutdemo.ui.screen.drawer.navigation

sealed class MainNavigation(val route: String) {
    object DrawerScreen : MainNavigation("drawerscreen")
    object AboutScreen : MainNavigation("aboutscreen")
    object SettingsScreen : MainNavigation("settingsscreen")
}

