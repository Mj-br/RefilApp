package es.refil.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.data.models.User
import es.refil.data.network.auth.AuthRepositoryImpl
import es.refil.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepositoryImpl

):ViewModel() {

    private val _state: MutableStateFlow<UserDetailState> = MutableStateFlow(UserDetailState())
    val state: StateFlow<UserDetailState> = _state.asStateFlow()

    fun addNewUser(uuid: String, email: String) {
       val user = User(
           uid = uuid,
           email = email,
           name = authRepository.currentUser?.displayName,
           points = 0,
           profilePictureUrl = authRepository.currentUser?.photoUrl?.toString()
       )
        userRepository.addNewUser(user)
    }

    fun getPoints() {
        viewModelScope.launch {
            try {
                val userUuid = authRepository.currentUser?.uid ?: ""
                val signInResult = userRepository.getUser(userUuid)
                val currentState = _state.value
                _state.value = currentState.copy(user = signInResult?.copy(points = signInResult.points), isLoading = false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }





}