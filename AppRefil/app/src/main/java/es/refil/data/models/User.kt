package es.refil.data.models

data class SignInResult (
    val data: User?,
    val errorMessage: String?
)
data class User(
    val uid: String,
    val email: String?,
    val name: String?,
    val points: Int,
    val bottles: Int
) {
    constructor() : this("", "", "", 0, 0)
}
