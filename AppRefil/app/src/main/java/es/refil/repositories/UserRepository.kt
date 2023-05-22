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
            userList.document(user.uuid).set(user)
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getPoints(userUuid: String): Int {
        return withContext(Dispatchers.IO) {
            try {
                val documentSnapshot = userList.document(userUuid).get().await()
                val user = documentSnapshot.toObject(User::class.java)
                user?.points ?: 0
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }
    }
}
