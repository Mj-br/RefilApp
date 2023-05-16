package es.refil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.BoxWithConstraints
import com.google.accompanist.navigation.animation.*
import dagger.hilt.android.AndroidEntryPoint
import es.refil.navigation.Destinations
import es.refil.navigation.RefillNavigation.addLogin
import es.refil.navigation.RefillNavigation.addProfile
import es.refil.navigation.RefillNavigation.addRegister
import es.refil.presentation.auth.AuthViewModel
import es.refil.presentation.mainMarket.MainMarketViewModel
import es.refil.ui.theme.AppRefilTheme

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //We create a viewModel to be able to access the MainMarketScreen
    private val mainMarketViewModel: MainMarketViewModel by viewModels()

    private val viewModel by viewModels<AuthViewModel>()


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



