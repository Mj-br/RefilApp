package es.refil.data.network.qrCode

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.refil.data.Resource
import javax.inject.Inject

class QrRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : QrRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun getQrCode(uuid: String): Resource<FirebaseUser> {
       TODO("Por hacer") /*return try {
           val

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure<FirebaseUser>(e)
        }*/
    }
}