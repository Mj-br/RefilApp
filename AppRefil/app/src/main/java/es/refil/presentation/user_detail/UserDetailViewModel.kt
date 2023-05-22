package es.refil.presentation.user_detail

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.data.models.User
import es.refil.data.network.auth.AuthRepositoryImpl
import es.refil.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepositoryImpl

):ViewModel() {

    private val _state: MutableState<UserDetailState> = mutableStateOf(UserDetailState())
    val state: State<UserDetailState>
        get() = _state

    fun addNewUser(email: String){
       val user = User(
           uuid = authRepository.currentUser?.uid ?: "",
           email = email,
           name = /*authRepository.currentUser?.email?.split("@")?.get(0) ?: "",*/ authRepository.currentUser?.displayName ?: "",
           points = 0
       )

        userRepository.addNewUser(user)
    }

}