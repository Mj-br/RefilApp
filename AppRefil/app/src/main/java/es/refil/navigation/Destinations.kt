package es.refil.navigation

import androidx.navigation.NamedNavArgument

sealed class Destinations(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object Login: Destinations("login", emptyList())
    object Register: Destinations("register", emptyList())
    object Profile: Destinations("profile", emptyList())
}
