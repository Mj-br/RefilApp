package es.refil.repositories

import com.google.firebase.firestore.CollectionReference
import es.refil.data.models.User
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
}