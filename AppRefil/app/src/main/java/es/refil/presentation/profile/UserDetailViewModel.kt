package es.refil.presentation.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.data.models.User
import es.refil.data.network.auth.AuthRepositoryImpl
import es.refil.data.network.await
import es.refil.repositories.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserDetailViewModel
@Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepositoryImpl

):ViewModel() {

    private val _state: MutableStateFlow<User> = MutableStateFlow(User())
    val state: StateFlow<User> = _state.asStateFlow()

    init {
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            try {
                _state.value = getDatafromFirestore()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

//NUEVO VIDEO

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun getDatafromFirestore(): User {
    val db = FirebaseFirestore.getInstance()
    var user = User()


    try {
        db.collection("users").get().await().map {
            val result = it.toObject(User::class.java)
            user = result
        }

    } catch (e: FirebaseFirestoreException) {
        Log.d("error", "Error getting documents: $e")
        e.printStackTrace()
    }
    return user
}

//TODO: (ERROR) NO FUNCIONA
//Get data from firestore in live NOT FUNCTION
suspend fun getDatafromFirestoreLive(): User {
    val db = FirebaseFirestore.getInstance()
    var user = User()
    val uuid = Firebase.auth.currentUser?.uid

    val docRef = uuid?.let { db.collection("users").document(it) }
    docRef?.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w(TAG, "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.exists()) {
            Log.d(TAG, "Current data: ${snapshot.data}")
        } else {
            Log.d(TAG, "Current data: null")
        }
    }
    return user
}