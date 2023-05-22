package es.refil.presentation.auth

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import es.refil.data.network.auth.AuthRepository
import es.refil.data.Resource
import es.refil.data.network.auth.AuthRepositoryImpl
import es.refil.presentation.auth.login.LoginStateData
import es.refil.presentation.auth.registration.RegisterStateData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    val loginState: MutableState<LoginStateData> = mutableStateOf(LoginStateData())
    val registerState: MutableState<RegisterStateData> = mutableStateOf(RegisterStateData())

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    //region Login and Signup functions

    //If we are already logged in, we don't need to do anything
    init {
        if (currentUser != null) {
            _loginFlow.value = Resource.Success(currentUser!!)
        }
    }

    //If we are not logged in, we need to do Login
    fun login(email:String, password:String) {

        val errorMessage = inputLoginError(email, password)

        errorMessage?.let {
            loginState.value = loginState.value.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {

            loginState.value = loginState.value.copy(displayProgressBar = true)

            delay(3000)

            loginState.value = loginState.value.copy(email = email, password = password)
            loginState.value = loginState.value.copy(displayProgressBar = false)
            loginState.value = loginState.value.copy(successLogin = true)

            _loginFlow.value = Resource.Loading
            val result = authRepository.login(email, password)
            _loginFlow.value = result

        }
    }





    //If we do not have an account, we need to do Signup
    fun signup(email: String, password: String, confirmPassword: String) {
        val errorMessage =
            inputSignUpError(email, password, confirmPassword)

        errorMessage?.let {
            registerState.value = registerState.value.copy(errorMessage = errorMessage)
            return
        }



        viewModelScope.launch {
            _signupFlow.value = Resource.Loading
            val result = authRepository.signUp(email, password)
            _signupFlow.value = result

        }


    }

    //Logout
    fun logout() = viewModelScope.launch {
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }

    fun hideLoginErrorDialog() {
        loginState.value = loginState.value.copy(
            errorMessage = null
        )
    }
    fun hideRegisterErrorDialog() {
        registerState.value = registerState.value.copy(
            errorMessage = null
        )
    }

    // Show button logic
    //fun enableLogin(email: String, password: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6

    private fun inputLoginError(email: String, password: String) =
        if (email.isBlank() || password.isBlank()) {
            R.string.error_input_empty
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            R.string.error_not_a_valid_email
        } else if (password.length < 6) {
            R.string.error_password_too_short

        } else null

    private fun inputSignUpError(
        email: String,
        password: String,
        confirmPassword: String = ""
    ) = if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
        R.string.error_input_empty
    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        R.string.error_not_a_valid_email
    } else if (password.length < 6) {
        R.string.error_password_too_short
    } else if (password != confirmPassword) {
        R.string.error_incorrectly_repeated_password
    } else null

    //endregion
}