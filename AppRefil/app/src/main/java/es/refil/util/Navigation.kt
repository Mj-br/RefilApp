package es.refil.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import es.refil.data.models.BottomNavItem
import es.refil.favorites.ui.FavoriteScreen
import es.refil.presentation.auth.login.LoginScreen
import es.refil.mainMarket.MainMarketScreen
import es.refil.navigation.Destinations
import es.refil.presentation.auth.AuthViewModel


//Creamos nuestro navegador para todas las pantallas
@Composable
fun Navigation(navController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "MainMarketScreen") {
        composable("MainMarketScreen") {
            MainMarketScreen()
        }

        composable("LoginScreen") {
            //TODO: Esto es para el bottom bar, arreglar luego.
            /*LoginScreen(navController, authViewModel)*/
        }

        composable("FavoriteScreen") {
            FavoriteScreen()
        }


    }
}

/*Creating BottomNavigationBar
 * We need to send some data to the BottomNavigationBar
 * so we can show the name in the bottom of icon
 * that's why we create a a Data class named BottomNavItem
* */
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavHostController, //We need the navHostController to know which screen we are on (to know if is selected or not)
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backstackEntry =
        navController.currentBackStackEntryAsState() // We set the variable backstackEntry to save the state of the current button
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray, //TODO: Cambiar el color a theme de la app
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected =
                item.route == backstackEntry.value?.destination?.route //We check if the item is selected, this helps to know if the item is selected comparing it the item itself (item.route) with the current destination
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    onItemClick(item)
                },
                selectedContentColor = Color.Green, //TODO: Cambiar el color a theme de la app
                unselectedContentColor = Color.Gray, //TODO: Cambiar el color a theme de la app
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )

                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )

                        }

                    }

                }
            )
        }

    }

}

