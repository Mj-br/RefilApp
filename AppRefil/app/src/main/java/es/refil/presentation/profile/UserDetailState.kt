package es.refil.presentation.profile

import es.refil.data.models.User

data class UserDetailState(
    val isLoading: Boolean = false,
    val user : User? = null,
    val error : String = ""
)
