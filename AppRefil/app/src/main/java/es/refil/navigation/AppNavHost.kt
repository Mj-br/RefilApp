package es.refil.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import es.refil.presentation.auth.AuthViewModel
import es.refil.presentation.auth.login.LoginScreen
import es.refil.presentation.auth.registration.RegistrationScreen
import es.refil.presentation.codeQR.QrScreen
import es.refil.presentation.codeQR.QrViewModel
import es.refil.presentation.profile.ProfileScreen


@ExperimentalAnimationApi
@Composable
fun AppNavHost(
    authViewModel: AuthViewModel, qrViewModel: QrViewModel?,
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
                viewModel = authViewModel,
                navController = navController,
                onNavigateToRegister = {
                    navController.navigate(Destinations.Register.route)
                },
                onDismissDialog = {
                    authViewModel.hideLoginErrorDialog()
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
                viewModel = authViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onDismissDialog = authViewModel::hideRegisterErrorDialog
            )

        }

        composable(
            route = Destinations.Profile.route,
            arguments = Destinations.Profile.arguments

        ) {
            ProfileScreen(authViewModel, navController)

        }

        composable(
            route = Destinations.QrScreen.route,
            arguments = Destinations.QrScreen.arguments
        ){
            QrScreen(qrViewModel, navController)
        }


        composable(
            route = Destinations.MainMarket.route,
            arguments = Destinations.MainMarket.arguments) {
        }


        composable(
            route = Destinations.Favorites.route,
            arguments = Destinations.Favorites.arguments) {
        }

    }


}


