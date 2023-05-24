package es.refil.presentation.profile

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.refil.R
import es.refil.data.models.User
import es.refil.navigation.Destinations
import es.refil.presentation.auth.AuthViewModel
import es.refil.ui.theme.AppRefilTheme
import es.refil.ui.theme.spacing


@Composable
fun ProfileScreen(
    viewModel: AuthViewModel? = hiltViewModel(),
    userViewModel: UserDetailViewModel = hiltViewModel(),
    navController: NavHostController,
    userData: User?,
    onSignOut: () -> Unit
) {
    val spacing = MaterialTheme.spacing

    val getData = userViewModel.state.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(spacing.medium)
            .padding(top = spacing.extraLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Welcome",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.size(25.dp))

        //User profile picture from Google
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Login_Image",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .clip(
                    CircleShape
                )
                .size(150.dp)

        )

        Spacer(modifier = Modifier.size(20.dp))

        if (userData?.name != null) {
            Text(
                text = userData.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.onSurface

            )
        } else {
            Text(
                text = viewModel?.currentUser?.displayName ?: "",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

        }

        Spacer(modifier = Modifier.padding(25.dp))

        Column(modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(spacing.medium)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(spacing.medium)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = stringResource(id = R.string.name),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(0.3f),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = viewModel?.currentUser?.displayName ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(0.7f),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = stringResource(id = R.string.points),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(0.3f),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = getData.value.points.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(0.3f),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                }

                Spacer(modifier = Modifier.size(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()

                ) {Text(
                    text = "Bottles",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(0.3f),
                    color = MaterialTheme.colorScheme.onSurface
                )

                    Text(
                        text = getData.value.bottles.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(0.3f),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = spacing.extraLarge),
                    //enabled = enabledButton() == true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3),
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                    onClick = {

                        navController.navigate(Destinations.QrScreen.route) {
                            popUpTo(Destinations.Profile.route) {
                                inclusive = true
                            }
                        }
                    },
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = "Generate QR",
                        style = MaterialTheme.typography.titleSmall
                    )
                }


                Button(
                    onClick =/* onSignOut*/ {
                        viewModel?.logout()
                        navController.navigate(Destinations.Login.route) {
                            popUpTo(Destinations.Profile.route) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = spacing.large),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text(text = stringResource(id = R.string.logout))
                }

            }

        }


    }
}

/*@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ProfileScreenPreview() {
    val user = User(
        uid = "",
        name = "",
        email = "",
        points = 0,
        bottles = 0,
        favorites = emptyList()
    )

    AppRefilTheme {
        ProfileScreen(
            viewModel = null,
            userViewModel = hiltViewModel(),
            navController = rememberNavController(),
            userData = user,
            onSignOut = {}
        )
    }
}*/
