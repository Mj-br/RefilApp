package es.refil.presentation.login

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val state: MutableState<LoginStateData> = mutableStateOf(LoginStateData())

    fun login(email: String, password: String) {

        val errorMessage = if (email.isBlank() || password.isBlank()) {
            R.string.error_input_empty
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            R.string.error_not_a_valid_email
        } else if (email != "user@gmail.com" || password != "password") {  // TODO: Change this later, this is for connect with FIREBASE LOGIN
            R.string.error_invalid_credentials
        } else null

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = it)
            return
        }

        //After the credentials are valid, we can login, THIS IS A SIMULATION TODO: Change this later

        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)

            delay(3000)

            state.value = state.value.copy(email = email, password = password)
            state.value = state.value.copy(displayProgressBar = false)
            state.value = state.value.copy(successLogin = true)
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}