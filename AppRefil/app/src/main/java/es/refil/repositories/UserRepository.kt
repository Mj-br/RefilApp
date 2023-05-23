package es.refil.repositories

import com.google.firebase.firestore.CollectionReference
import es.refil.data.models.User
import es.refil.data.network.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userList: CollectionReference
) {

    fun addNewUser(user: User){
        try {
            userList.document(user.uid).set(user)
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getUser(userUuid: String): User? {
        return withContext(Dispatchers.IO) {
            try {
                val documentSnapshot = userList.document(userUuid).get().await()
                documentSnapshot.toObject(User::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

