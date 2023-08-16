package es.refil.common.components.lotties

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import es.refil.R

@Composable
fun LottiePlantAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.planta),
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(50.dp),
        contentScale = ContentScale.Fit,
    )
}

@Composable
fun LottieReciclerAnimation(alpha: Float) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.recicler),
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(250.dp),
        contentScale = ContentScale.Fit,
    )


}

@Composable
fun LottieRecyclerAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.recyler),
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(500.dp),
        contentScale = ContentScale.Fit
    )


}

