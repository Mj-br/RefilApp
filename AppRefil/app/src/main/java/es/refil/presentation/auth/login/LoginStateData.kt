package es.refil.presentation.auth.login

import android.widget.Button
import androidx.annotation.StringRes


data class LoginStateData(
    val email: String = "",
    val password: String = "",
    val successLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val signInError: String? = null
)
