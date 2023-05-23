package es.refil.presentation.auth.registration

import androidx.annotation.StringRes
import es.refil.data.models.User

data class RegisterStateData(
    val isSignInSuccesful : Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val signInError: String? = null,
    val user : User? = null,


    )
