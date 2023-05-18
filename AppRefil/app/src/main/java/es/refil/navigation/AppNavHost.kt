package es.refil.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import es.refil.presentation.auth.AuthViewModel
import es.refil.presentation.auth.login.LoginScreen
import es.refil.presentation.auth.registration.RegistrationScreen
import es.refil.presentation.profile.ProfileScreen


@ExperimentalAnimationApi
@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String = Destinations.Login.route
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
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
            //We pass all we need to the LoginScreen, before this we create parameters to the LoginScreen
            LoginScreen(
                viewModel = viewModel,
                navController = navController,
                onNavigateToRegister = {
                    navController.navigate(Destinations.Register.route)
                },
                onDismissDialog = {
                    viewModel.hideErrorDialog()
                }
            )
        }

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
            //We pass all we need to the RegisterScreen, before this we create parameters to the RegisterScreen
            RegistrationScreen(
                navController = navController,
                viewModel = viewModel,
                state = viewModel.registerState.value,
                onBack = {
                    navController.popBackStack()
                },
                onDismissDialog = viewModel::hideErrorDialog
            )

        }



        composable(
            //We receive the email and password from the LoginScreen
            route = Destinations.Profile.route + "/{email}" + "/{password}",
            arguments = Destinations.Profile.arguments

        ) {
            ProfileScreen(viewModel, navController)

        }


    }
}
