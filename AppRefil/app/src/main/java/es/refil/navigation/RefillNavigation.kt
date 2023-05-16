package es.refil.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import es.refil.presentation.login.LoginScreen
import es.refil.presentation.login.LoginViewModel
import es.refil.presentation.profile.ProfileScreen
import es.refil.presentation.registration.RegisterViewModel
import es.refil.presentation.registration.RegistrationScreen

object RefillNavigation {

    @ExperimentalAnimationApi
    fun NavGraphBuilder.addLogin(
        navController: NavController
    ) {
        composable(
            route = Destinations.Login.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.Register.route ->
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Destinations.Register.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Destinations.Register.route ->
                        slideInHorizontally(
                            initialOffsetX = { -300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    else -> null
                }
            }

        ) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val email = loginViewModel.state.value.email
            val password = loginViewModel.state.value.password

            //We pass all we need to the LoginScreen, before this we create parameters to the LoginScreen
            if (loginViewModel.state.value.successLogin) {
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(
                        //We pass the email and password to the ProfileScreen TODO: FIRESTORE, WE NEED TO PASS THIS TO THE FIRESTORE
                        Destinations.Profile.route + "/$email" +"/$password"
                    ) {

                        //We do the pop up in order to go back NOT to the Login Screen but going out the app
                        popUpTo(Destinations.Login.route) {
                            inclusive = true
                        }
                    }
                }


            } else {
                LoginScreen(
                    state = loginViewModel.state.value,
                    onLogin = loginViewModel::login,
                    onNavigateToRegister = {
                        navController.navigate(Destinations.Register.route)
                    },
                    onDismissDialog = {
                        loginViewModel.hideErrorDialog()
                    }
                )
            }

        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.addRegister(
        navController: NavController
    ) {

        composable(

            route = Destinations.Register.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Destinations.Login.route ->
                        slideInHorizontally(
                            initialOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Destinations.Login.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Destinations.Login.route ->
                        slideOutHorizontally(
                            targetOffsetX = { 300 },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    else -> null
                }
            }

        ) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            //We pass all we need to the RegisterScreen, before this we create parameters to the RegisterScreen
            RegistrationScreen(
                state = registerViewModel.state.value,
                onRegister = registerViewModel::register,
                onBack = {
                    navController.popBackStack()
                },
                onDismissDialog = registerViewModel::hideErrorDialog
            )

        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun NavGraphBuilder.addProfile(
    ) {
        composable(
            //We receive the email and password from the LoginScreen
            route = Destinations.Profile.route + "/{email}" + "/{password}",
            arguments = Destinations.Profile.arguments

        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""

            ProfileScreen(email, password)

        }
    }
}