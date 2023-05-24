package es.refil.navigation

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.identity.Identity
import es.refil.presentation.auth.AuthViewModel
import es.refil.presentation.auth.login.LoginScreen
import es.refil.presentation.auth.registration.GoogleAuthUiClient
import es.refil.presentation.auth.registration.RegistrationScreen
import es.refil.presentation.codeQR.QrScreen
import es.refil.presentation.codeQR.QrViewModel
import es.refil.presentation.components.AnimatedSplashScreen
import es.refil.presentation.profile.ProfileScreen
import es.refil.presentation.profile.UserDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@Composable
fun AppNavHost(
    authViewModel: AuthViewModel, qrViewModel: QrViewModel?, userDetailViewModel: UserDetailViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String = Destinations.SplashScreen.route
) {

    val context = LocalContext.current
    val googleAuthUiClient by remember(context) {
        mutableStateOf(
            GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context)
            )
        )
    }

    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = Destinations.SplashScreen.route
        ){
            AnimatedSplashScreen(navController = navController)
        }


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
                },
                state = authViewModel.loginState.value

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

            //Google entry
            val state by authViewModel.signInState.collectAsState()

            LaunchedEffect(key1 = Unit){
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate(Destinations.Profile.route)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        val scope = CoroutineScope(Dispatchers.Main)
                        scope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            authViewModel.onSignInResult(signInResult)
                        }

                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccesful) {
                if (state.isSignInSuccesful) {
                    Toast.makeText(context, "Sign in successful", Toast.LENGTH_LONG).show()

                    navController.navigate(Destinations.Profile.route)
                    authViewModel.resetState()
                }
            }

            //We pass all we need to the RegisterScreen, before this we create parameters to the RegisterScreen
            RegistrationScreen(
                navController = navController,
                viewModel = authViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onDismissDialog = authViewModel::hideRegisterErrorDialog,
                state = state,
                onSignInClick = {

                    //LifecycleScope variant
                    val scope = CoroutineScope(Dispatchers.Main)
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )

                    }

                }

            )

        }

        composable(
            route = Destinations.Profile.route,
            arguments = Destinations.Profile.arguments

        ) {

            ProfileScreen(
                authViewModel,
                userDetailViewModel,
                navController,
                userData = googleAuthUiClient.getSignedInUser(),
            ) {
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    googleAuthUiClient.signOut()
                    Toast.makeText(context, "Signed out", Toast.LENGTH_LONG).show()

                    navController.popBackStack()
                }
            }

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




