package es.refil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import es.refil.core.ui.ScaffoldMain
import es.refil.login.ui.LoginScreen
import es.refil.mainMarket.MainMarketScreen
import es.refil.navigation.Destinations
import es.refil.presentation.mainMarket.MainMarketViewModel
import es.refil.presentation.profile.ProfileScreen
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
                    ){
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
){
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

    ){
        LoginScreen()

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
        RegistrationScreen()

    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addHome(
) {
    composable(
        route = Destinations.Home.route,

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
