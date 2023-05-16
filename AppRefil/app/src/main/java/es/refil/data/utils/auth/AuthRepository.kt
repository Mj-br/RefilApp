package es.refil.data.utils.auth

import com.google.firebase.auth.FirebaseUser
import es.refil.data.Resource

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signUp(email: String, password: String): Resource<FirebaseUser>
    fun logout()
}