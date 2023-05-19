package es.refil.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destinations(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object Login: Destinations("LoginScreen", emptyList())
    object Register: Destinations("RegistrationScreen", emptyList())
    object Profile: Destinations(
        "ProfileScreen",
        emptyList()
        /*listOf(
            navArgument("email"){type = NavType.StringType},
            navArgument("password"){type = NavType.StringType}

        )*/
    )

    object QrScreen: Destinations("QrScreen", emptyList())
    object MainMarket: Destinations("MainMarketScreen", emptyList())
    object Favorites: Destinations("FavoriteScreen", emptyList())
    object DetailProduct: Destinations("DetailProductScreen", emptyList())

}
