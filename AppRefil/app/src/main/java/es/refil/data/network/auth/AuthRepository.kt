package es.refil.data.network.auth

import com.google.firebase.auth.FirebaseUser
import es.refil.data.Resource

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signUp(email: String, password: String): Resource<FirebaseUser>
    //suspend fun getQrCode(uuid: String): Resource<FirebaseUser> //TODO: QRCODE
    fun logout()
}