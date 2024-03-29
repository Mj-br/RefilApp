package es.refil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.BoxWithConstraints
import dagger.hilt.android.AndroidEntryPoint
import es.refil.navigation.AppNavHost
import es.refil.presentation.auth.AuthViewModel
import es.refil.presentation.mainMarket.MainMarketViewModel
import es.refil.presentation.profile.UserDetailViewModel
import es.refil.ui.theme.AppRefilTheme

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //We create a viewModel to be able to access the MainMarketScreen
    private val mainMarketViewModel: MainMarketViewModel by viewModels()

    private val viewModel by viewModels<AuthViewModel>()
    private val userViewModel by viewModels<UserDetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            AppRefilTheme {
                        BoxWithConstraints {

                            AppNavHost(viewModel, userViewModel)

                        }
            }
        }

    }

}



