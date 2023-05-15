package es.refil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import es.refil.presentation.login.LoginScreen
import es.refil.navigation.Destinations
import es.refil.presentation.login.LoginViewModel
import es.refil.presentation.mainMarket.MainMarketViewModel
import es.refil.presentation.profile.ProfileScreen
import es.refil.presentation.registration.RegisterViewModel
import es.refil.presentation.registration.RegistrationScreen
import es.refil.ui.theme.AppRefilTheme

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //We create a viewModel to be able to access the MainMarketScreen
    private val mainMarketViewModel: MainMarketViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            AppRefilTheme {
                val navController = rememberAnimatedNavController()
                // A surface container using the 'background' color from the theme
                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Destinations.Login.route
                    ) {
                        addLogin(navController)
                        addRegister(navController)
                        addHome()

                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addLogin(
    navController: NavController
) {
    composable(
        route = Destinations.Login.route,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(500)
            )

        },
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popExitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(500)
            )
        }

    ) {
        val loginViewModel: LoginViewModel = hiltViewModel()

        //We pass all we need to the LoginScreen, before this we create parameters to the LoginScreen
        if (loginViewModel.state.value.successLogin) {
            LaunchedEffect(key1 = Unit) {
                navController.navigate(Destinations.Profile.route) {

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
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(500)
            )

        },
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(500)
            )
        },
        popExitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(500)
            )
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
fun NavGraphBuilder.addHome(
) {
    composable(
        route = Destinations.Profile.route,

        ) {
        ProfileScreen()

    }
}


/* Default Composable
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppRefilTheme {
        Greeting("Android")
    }
}*/
