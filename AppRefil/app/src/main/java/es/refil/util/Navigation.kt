package es.refil.core.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.refil.favorites.ui.FavoriteScreen
import es.refil.mainMarket.MainMarketScreen



//Creamos nuestro navegador para todas las pantallas
@Composable
fun Navigation(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = "MainMarketScreen",
    ) {

        composable("MainMarketScreen") {
            MainMarketScreen()
        }

        composable("LoginScreen") {
            //TODO: Esto es para el bottom bar, arreglar luego.
        }

        composable("FavoriteScreen") {
            FavoriteScreen()
        }


    }
}
