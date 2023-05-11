package es.refil.mainMarket

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MainMarketScreen(

/* TODO: Comentamos solo para tener la preview de la pantalla
mainMarketViewModel: MainMarketViewModel*/
) {
    //Creamos un center text
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Market Screen")
    }

}

