package es.refil.data.models

data class SignInResult (
    val data: User?,
    val errorMessage: String?
)
data class User(
    val uuid: String,
    val email: String?,
    val name: String?,
    val points: Int,
    val profilePictureUrl: String?
) {
    constructor() : this("", "", "", 0, "")
}
