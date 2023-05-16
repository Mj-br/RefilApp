package es.refil.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destinations(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object Login: Destinations("login", emptyList())
    object Register: Destinations("register", emptyList())
    object Profile: Destinations(
        "profile",
        listOf(
            navArgument("email"){type = NavType.StringType},
            navArgument("password"){type = NavType.StringType}

        )
    )
}
