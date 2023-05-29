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

}

