package es.refil.presentation.registration

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class RegisterViewModel: ViewModel() {

    val state: MutableState<RegisterStateData> = mutableStateOf(RegisterStateData())

    fun register(
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val errorMessage = if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            R.string.error_input_empty
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            R.string.error_not_a_valid_email
        } else if(password != confirmPassword) {
            R.string.error_incorrectly_repeated_password
        } else null

        errorMessage?.let {
            state.value = state.value.copy(errorMessage = errorMessage)
            return
        }


        //SIMULATION TODO: REMOVE later
        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)

            delay(3000)

            state.value = state.value.copy(displayProgressBar = false)
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}