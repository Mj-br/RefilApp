package es.refil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import com.google.accompanist.navigation.animation.*
import dagger.hilt.android.AndroidEntryPoint
import es.refil.core.ui.ScaffoldMain
import es.refil.presentation.login.LoginScreen
import es.refil.navigation.Destinations
import es.refil.navigation.RefillNavigation.addLogin
import es.refil.navigation.RefillNavigation.addProfile
import es.refil.navigation.RefillNavigation.addRegister
import es.refil.presentation.login.LoginViewModel
import es.refil.presentation.mainMarket.MainMarketViewModel
import es.refil.presentation.profile.ProfileScreen
import es.refil.presentation.registration.RegisterViewModel
import es.refil.presentation.registration.RegistrationScreen
import es.refil.ui.theme.AppRefilTheme

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //We create a viewModel to be able to access the MainMarketScreen
    private val mainMarketViewModel: MainMarketViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRefilTheme {
                 val navController = rememberAnimatedNavController()

                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Destinations.Login.route
                    ) {

                        //TODO: Need to add a bottom bar with Scaffold

                        addLogin(navController)
                        addRegister(navController)
                        addProfile()

                    }

                }
            }
        }
    }
}



