package es.refil.presentation.auth.registration

import androidx.annotation.StringRes

data class RegisterStateData(
    val isSignInSuccesful : Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val signInError: String? = null


)
