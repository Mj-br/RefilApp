package es.refil.presentation.user_detail

import es.refil.data.models.User

data class UserDetailState(
    val isLoading: Boolean = false,
    val user : User? = null,
    val error : String = "",
    val points : Int = 0
)
