package es.refil.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateQrCode(
    uuid: String
) {
    val writer = MultiFormatWriter()
    val bitMatrix = writer.encode(uuid, BarcodeFormat.QR_CODE, 350, 350)
    val encoder = BarcodeEncoder()
    val bitmap = encoder.createBitmap(bitMatrix)

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "QR Code",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    DisposableEffect(Unit) {
        // Oculta el teclado cuando se desecha el efecto
        onDispose {
            softwareKeyboardController?.hide()
        }
    }
}

