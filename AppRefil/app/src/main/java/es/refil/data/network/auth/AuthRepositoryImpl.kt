package es.refil.data.network.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import es.refil.data.Resource
import es.refil.data.models.User
import es.refil.repositories.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository,
    //The oneTapClient show us the dialog to sign in
) : AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
            val user = firebaseAuth.createUserWithEmailAndPassword(email, password).await()?.user

            // Actualizar el perfil del usuario con el nombre
            user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(email.split("@")[0]).build())?.await()

            // Crear un nuevo usuario en Firestore
            createNewFireStoreUser(user)


            //TODO: (ERROR) AQUI NO SE LE ESTA MANDANDO EL NEW USER
            Resource.Success(user!!)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure<FirebaseUser>(e)

        }


    }

    private fun createNewFireStoreUser(user: FirebaseUser?) {
        val newUser = User(
            uid = user?.uid ?: "",
            email = user?.email,
            name = user?.displayName,
            points = 0,
            bottles = 0,
            favorites = emptyList()

        )
        userRepository.addNewUser(newUser)
    }


    override fun logout() {
        firebaseAuth.signOut()
    }



}