package es.refil.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import es.refil.data.models.BottomNavItem


@Composable
fun ScaffoldMain() {
    val navController = rememberNavController()
    //TODO: Tengo que ver el video de Aris donde poner el Scaffold o simplemente ir con Phillip y meterlo directamente.

        Scaffold(
        /*TODO: Pasamos el toolbar
        TopAppBar() {

        }*/

        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "MainMarketScreen",
                        icon = Icons.Default.Home,
                        badgeCount = 0
                    ),
                    BottomNavItem(
                        name = "Profile",
                        route = "LoginScreen",
                        icon = Icons.Default.Person,
                        badgeCount = 0
                    ),
                    BottomNavItem(
                        name = "Favorites",
                        route = "FavoriteScreen",
                        icon = Icons.Default.Favorite,
                        badgeCount = 0
                    ),

                    ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route) //If it refers to this item (screen), it will navigate to it
                }
            )
        }

    ) {
        Box(modifier = Modifier.padding(it)) {
            Navigation(navController = navController)
        }
    }

}