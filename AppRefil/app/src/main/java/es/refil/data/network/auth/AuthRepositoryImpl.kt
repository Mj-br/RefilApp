package es.refil.data.network.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import es.refil.data.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure<FirebaseUser>(e)

        }
    }

    override suspend fun signUp(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(email.split("@")[0]).build())?.await() //TODO: We need to change this because we are using email and we need to separate the name from the email
            Resource.Success(result.user!!)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure<FirebaseUser>(e)

        }


    }



    override fun logout() {
        firebaseAuth.signOut()
    }
}