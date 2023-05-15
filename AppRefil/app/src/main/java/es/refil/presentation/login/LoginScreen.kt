package es.refil.presentation.login

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import es.refil.R
import es.refil.presentation.components.EventDialog
import es.refil.presentation.components.RoundedButton
import es.refil.presentation.components.TransparentTextField

@Composable
fun LoginScreen(
    state: LoginStateData,
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onDismissDialog: () -> Unit
) {

    //Todo: We can change this later to do a good Clean Architecture

    val emailValue = rememberSaveable { mutableStateOf("") }
    val passwordValue = rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

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
                                        onLogin(emailValue.value, passwordValue.value)

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
                                displayProgressBar = state.displayProgressBar,
                                onClick = {
                                    //We pass values to LOGIN
                                    onLogin(emailValue.value, passwordValue.value)
                                }
                            )

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

        if (state.errorMessage != null) {
            EventDialog(
                errorMessage = state.errorMessage,
                onDismiss = onDismissDialog
            )
        }
    }

}