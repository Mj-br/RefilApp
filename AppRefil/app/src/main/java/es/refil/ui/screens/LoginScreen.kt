package es.refil.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
/* TODO: Comentamos solo para tener la preview de la pantalla
mainMarketViewModel: LoginViewModel*/
) {
    //Creamos un center text
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "LoginScreen")
    }
}