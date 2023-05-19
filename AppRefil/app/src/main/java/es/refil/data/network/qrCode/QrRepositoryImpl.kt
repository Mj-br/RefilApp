package es.refil.data.network.qrCode

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.zxing.*
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import es.refil.data.Resource
import java.util.*
import javax.inject.Inject

class QrRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : QrRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    /*override suspend fun generateQrCode(uuid: String): BitMatrix? {
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L

        val writer = MultiFormatWriter()
        try {
            return writer.encode(firebaseAuth.currentUser?.uid, BarcodeFormat.QR_CODE, 512, 512, hints)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }*/
}

