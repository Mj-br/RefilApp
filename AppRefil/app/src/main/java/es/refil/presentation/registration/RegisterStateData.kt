package es.refil.presentation.registration

import androidx.annotation.StringRes

data class RegisterStateData(
    val successRegister: Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null

)
