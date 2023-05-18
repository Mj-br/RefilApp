package es.refil.data.network.qrCode

import com.google.firebase.auth.FirebaseUser
import dagger.Provides
import es.refil.data.Resource

interface QrRepository {
    val currentUser: FirebaseUser?


    suspend fun getQrCode(uuid: String): Resource<FirebaseUser>


}