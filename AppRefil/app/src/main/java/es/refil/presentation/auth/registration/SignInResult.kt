package es.refil.presentation.auth.registration

data class SignInResult (
    val data: UserData?,
    val errorMessage: String?
    )

data class UserData(
    val uuid: String,
    val email: String?,
    val name: String?,
    val points: Int,
    val profilePictureUrl: String?

)