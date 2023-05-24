package es.refil.presentation.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import es.refil.R
import es.refil.data.Resource
import es.refil.navigation.Destinations
import es.refil.presentation.auth.AuthViewModel
import es.refil.common.components.EventDialog
import es.refil.common.components.RoundedButton
import es.refil.common.components.TransparentTextField
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    viewModel: AuthViewModel? = hiltViewModel(),
    navController: NavController,
    onNavigateToRegister: () -> Unit,
    onDismissDialog: () -> Unit,
    state: LoginStateData
) {

    //Todo: We can change this later to do a good Clean Architecture

    //Error handling
    val context = LocalContext.current

    val emailValue = rememberSaveable { mutableStateOf("") }
    val passwordValue = rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val loginFlow = viewModel?.loginFlow?.collectAsState()
    val stateText = viewModel?.loginState

    //Header
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    )
    {
        Image(
            painter = painterResource(id = R.drawable.logox),
            contentDescription = "Login_Image",
            contentScale = ContentScale.Inside
        )

        //Body with textField
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            ConstraintLayout {

                val (surface, fab) = createRefs()

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .constrainAs(surface) {
                            bottom.linkTo(parent.bottom)
                        },
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStartPercent = 8,
                        topEndPercent = 8
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Welcome", style = MaterialTheme.typography.h4.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )

                        Text(
                            text = "Login to your account",
                            style = MaterialTheme.typography.h5.copy(
                                color = MaterialTheme.colors.primary
                            )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TransparentTextField(
                                textFieldValue = emailValue,
                                textLabel = "Email",
                                keyboardType = KeyboardType.Email,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                imeAction = ImeAction.Next
                            )

                            TransparentTextField(
                                textFieldValue = passwordValue,
                                textLabel = "Password",
                                keyboardType = KeyboardType.Password,
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()

                                        //We pass values to LOGIN
                                        viewModel?.login(emailValue.value, passwordValue.value)

                                    }
                                ),
                                imeAction = ImeAction.Done,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            passwordVisibility = !passwordVisibility
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (passwordVisibility) {
                                                Icons.Filled.Visibility
                                            } else {
                                                Icons.Filled.VisibilityOff

                                            },
                                            contentDescription = "Toggle Password Icon"
                                        )

                                    }
                                },
                                visualTransformation = if (passwordVisibility) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                }

                            )

                            Text(
                                modifier = Modifier.fillMaxWidth(), text = "Forgot Password?",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.End
                            )


                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                                RoundedButton(
                                    text = "Login",
                                    //enabledButton = { viewModel?.enableLogin(emailValue.value, passwordValue.value) },
                                    displayProgressBar = loginFlow?.value == Resource.Loading
                                ) {
                                    viewModel?.login(emailValue.value, passwordValue.value)
                                }


                            ClickableText(
                                text = buildAnnotatedString {
                                    append("Do not have an account? ")

                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colors.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    ) {
                                        append("Sign Up")

                                    }
                                },
                                onClick = {
                                    //We pass the NAVIGATION to REGISTER
                                    onNavigateToRegister()
                                }
                            )

                        }

                    }

                }


                //TODO: CAMBIAR ESTO SI CARGA MUY GRANDE
                FloatingActionButton(
                    modifier = Modifier
                        .size(72.dp)
                        .constrainAs(fab) {
                            top.linkTo(surface.top, margin = (-36).dp)
                            end.linkTo(surface.end, margin = 36.dp)
                        },
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = {
                        //We pass the NAVIGATION to REGISTER
                        onNavigateToRegister()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(42.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Forward Icon",
                        tint = Color.White
                    )

                }

            }

        }

        if (stateText?.value?.errorMessage != null) {
            EventDialog(
                errorMessage = stateText.value.errorMessage!!,
                onDismiss = onDismissDialog
            )
        }


        loginFlow?.value?.let { resource ->
            when (resource) {
                is Resource.Failure<*> -> {
                    LaunchedEffect(Unit) {
                        val exception = resource.exception
                        viewModel.showErrorMessage(exception)

                        val errorMessage = viewModel.loginState.value.signInError
                        Toast.makeText(
                            context,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
                Resource.Loading -> {
                    if (Resource.Loading == loginFlow.value){
                        viewModel.showLoginButton()
                    }

                    /*ConstraintLayout(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val (refLoader) = createRefs()


                        CircularProgressIndicator(
                            modifier = Modifier.constrainAs(refLoader) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }

                        )
                    }*/
                }

                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(
                            Destinations.Profile.route
                        ) {
                            popUpTo(Destinations.Login.route) { inclusive = true }
                        }
                    }

                }
            }
        }


    }



}