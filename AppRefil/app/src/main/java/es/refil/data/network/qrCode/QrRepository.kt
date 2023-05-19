package es.refil.data.network.qrCode

import com.google.firebase.auth.FirebaseUser
import com.google.zxing.common.BitMatrix
import dagger.Provides
import es.refil.data.Resource

interface QrRepository {
    val currentUser: FirebaseUser?
//    suspend fun generateQrCode(uuid: String): BitMatrix?


}