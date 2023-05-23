package es.refil.data.network.auth

import com.google.firebase.auth.FirebaseUser
import es.refil.data.Resource
import es.refil.data.models.User

interface AuthRepository {
    val currentUser: FirebaseUser?


    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signUp(email: String, password: String): Resource<User>
    fun logout()
}