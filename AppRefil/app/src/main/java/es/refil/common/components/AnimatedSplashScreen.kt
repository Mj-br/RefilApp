package es.refil.common.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Recycling
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import es.refil.R
import es.refil.navigation.Destinations
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen( navController: NavHostController) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000

        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay (3000)
        navController.popBackStack()
        navController.navigate(Destinations.Login.route)
    }


    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color.Black else Color(0xD7CFDDE9))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(bottom = 25.dp)
                    .alpha(alpha),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                text = "Refill",


            )

            Icon(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alpha = alpha),
                imageVector = Icons.Rounded.Recycling, contentDescription = "Logo Icon",
                tint = Color(0xFF2196F3)

            )


        }


        }


    }

@Preview(showBackground = true)
@Composable
fun AnimatedSplashScreenPreview() {
    Splash(alpha = 1f)
}