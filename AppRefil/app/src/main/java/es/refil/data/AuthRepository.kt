package es.refil.data

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signUp(email: String, password: String): Resource<FirebaseUser>
    fun logout()
}